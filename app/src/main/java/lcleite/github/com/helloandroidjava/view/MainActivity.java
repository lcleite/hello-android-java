package lcleite.github.com.helloandroidjava.view;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import lcleite.github.com.helloandroidjava.R;
import lcleite.github.com.helloandroidjava.async.GetTweetsTask;
import lcleite.github.com.helloandroidjava.model.Tweet;
import lcleite.github.com.helloandroidjava.utils.AndroidUtils;
import lcleite.github.com.helloandroidjava.utils.StringUtils;
import lcleite.github.com.helloandroidjava.view.list.SearchResultAdapter;
import lcleite.github.com.helloandroidjava.view.list.SearchResultItemDivider;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, GetTweetsTask.Callback, SearchResultAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener, TextView.OnEditorActionListener {

    public static final String EXTRA_TWEET = "lcleite.github.com.MainActivity.EXTRA_TWEET";

    private SwipeRefreshLayout refreshContainer;
    private Toolbar appBar;
    private String lastQuery;
    private EditText etSearch;
    private ImageButton ibSearch;
    private RecyclerView searchResultRecyclerView;
    private SearchResultAdapter searchResultAdapter;
    private List<Tweet> searchResults;
    private ProgressBar pbarLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initAppBar();
        initViews();
    }

    private void initAppBar() {
        appBar = (Toolbar) findViewById(R.id.appBar);
        appBar.setTitle(R.string.app_name);
        setSupportActionBar(appBar);
    }

    private void initViews() {
        initSearchViews();
        initRefreshContainer();
        initRecyclerView();
    }

    private void initSearchViews() {
        etSearch = (EditText) findViewById(R.id.etSearch);
        etSearch.setOnEditorActionListener(this);
        ibSearch = (ImageButton) findViewById(R.id.ibSearch);
        ibSearch.setOnClickListener(this);
        pbarLoading = (ProgressBar) findViewById(R.id.pbarLoading);
    }

    private void initRefreshContainer() {
        lastQuery = null;
        refreshContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        refreshContainer.setOnRefreshListener(this);
        refreshContainer.setColorSchemeColors(getColor(R.color.colorPrimary), getColor(R.color.colorAccent));
    }

    private void initRecyclerView() {
        searchResults = new ArrayList<>();
        searchResultRecyclerView = (RecyclerView) findViewById(R.id.rvSearchResult);
        searchResultAdapter = new SearchResultAdapter(searchResults);

        searchResultAdapter.setOnItemClickListener(this);
        searchResultRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchResultRecyclerView.setAdapter(searchResultAdapter);
        searchResultRecyclerView.addItemDecoration(
            new SearchResultItemDivider(ContextCompat.getDrawable(this, R.drawable.search_result_divider)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuSettings:
                goToSettings();
        }

        return super.onOptionsItemSelected(item);
    }

    private void goToSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        searchTweet();
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            searchTweet();
            return true;
        }

        return false;
    }

    private void searchTweet() {
        String query = createSearchQuery();

        setLoading(true);
        saveSearchTermForRefreshAction(query);
        executeSearchTask(query);
    }

    private String createSearchQuery(){
        String searchQuery = etSearch.getText().toString();

        return StringUtils.encodeSearchTerm(searchQuery);
    }

    private void saveSearchTermForRefreshAction(String query){
        lastQuery = query;
    }

    @Override
    public void setSearchResult(List<Tweet> results) {
        setRefreshing(false);
        updateResultList(results);
        setLoading(false);
        AndroidUtils.hideSoftKeyboard(this, ibSearch);
    }

    private void setRefreshing(boolean refreshing){
        if(refreshContainer.isRefreshing())
            refreshContainer.setRefreshing(refreshing);

    }

    private void executeSearchTask(String query){
        new GetTweetsTask(this).execute(query);

    }

    private void updateResultList(List<Tweet> results){
        searchResults.clear();
        searchResults.addAll(results);
        searchResultAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int position) {
        Tweet tweet = searchResults.get(position);
        Intent intent = new Intent(this, TweetActivity.class);

        intent.putExtra(EXTRA_TWEET, tweet);
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        if(lastQuery != null)
            executeSearchTask(lastQuery);
        else
            setRefreshing(false);
    }

    public void setLoading(boolean loading) {
        if(loading)
            pbarLoading.setVisibility(View.VISIBLE);
        else
            pbarLoading.setVisibility(View.GONE);
    }
}



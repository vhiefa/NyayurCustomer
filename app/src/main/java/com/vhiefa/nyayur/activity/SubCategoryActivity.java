package com.vhiefa.nyayur.activity;

import android.support.v7.app.ActionBarActivity;

public class SubCategoryActivity extends ActionBarActivity {// implements LoaderCallbacks<Cursor> {

/*private SubCategoryProductAdapter mEventAdapter;
public String kat;

private static final int SAYUR_LOADER = 0;

// For the ketersediaan view we're showing only a small subset of the stored data.
// Specify the columns we need.
private static final String[] SAYUR_COLUMNS = {
        KetersediaanEntry.TABLE_NAME + "." + KetersediaanEntry._ID,
        KetersediaanEntry.COLUMN_ID_KETERSEDIAAN,
        KetersediaanEntry.COLUMN_CATEGORY,
        KetersediaanEntry.COLUMN_SUBCATEGORY,
        KetersediaanEntry.COLUMN_TUKANG_ID,
        KetersediaanEntry.COLUMN_ITEM_CODE,
        KetersediaanEntry.COLUMN_ITEM_NAME,
        KetersediaanEntry.COLUMN_ICON_CODE,
        KetersediaanEntry.COLUMN_HARGA,
        KetersediaanEntry.COLUMN_TANGGAL
        };


// These indices are tied to EVENT_COLUMNS.  If EVENT_COLUMNS changes, these
// must change.
public static final int COL_ID = 0;
public static final int COL_EVENT_ID = 1;
public static final int COL_EVENT_TITLE = 2;
public static final int COL_EVENT_DATE = 3;
public static final int COL_EVENT_VENUE = 4;
public static final int COL_EVENT_DESCRIPTION = 5;
public static final int COL_EVENT_CATEGORY = 6;
public static final int COL_EVENT_ORGANIZER = 7;


public EventFragment() {
        }

@Override
public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);
        //  kat = getActivity().getTitle().toString();
        }


@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {

        Bundle b = getActivity().getIntent().getExtras();
        if (b!=null) {
        kat = b.getString("kategori");
        getActivity().setTitle(kat);
        }

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        // Get a reference to the ListView, and attach this adapter to it.
        ListView listView = (ListView) rootView.findViewById(R.id.listview_event);

        mEventAdapter = new EventAdapter (getActivity(),null, 0);

        listView.setAdapter(mEventAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

@Override
public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Cursor cursor = mEventAdapter.getCursor();
        if (cursor != null && cursor.moveToPosition(position)) {
        ((Callback) getActivity())
        .onItemSelected(cursor.getString(COL_ID));
        }
        }
        });


        return rootView;
        }

@Override
public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.eventfragment, menu);
        }

@Override
public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if ((id == R.id.action_refresh)) {
        updateEvent();
        return true;
        }
        return super.onOptionsItemSelected(item);
        }

public interface Callback{
    public void onItemSelected(String id);
}

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(EVENT_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    public void updateEvent() {
        // WhatsOnUndipSyncAdapter.syncImmediately(getActivity());
        new FetchEventTask(getActivity()).execute();
    }

    @Override
    public void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(EVENT_LOADER, null, this);
    }

    @Override
    public void onStart() {
        super.onStart();
        updateEvent();
    }



    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // This is called when a new Loader needs to be created.  This
        // fragment only uses one loader, so we don't care about checking the id.
        // To only show current and future dates, get the String representation for today,
        // and filter the query to return event only for dates after or including today.


        Uri produkUri;
        produkUri = KetersediaanEntry.buildKetersedianBasedTukangAndCategoryAndSubCategory(tukang, kat, subkat);
        Log.v("buildEventWithStartDate", produkUri.toString());
        // Now create and return a CursorLoader that will take care of
        // creating a Cursor for the data being displayed.
        return new CursorLoader(
                this,//   getActivity(),
                produkUri,
                SAYUR_COLUMNS,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        if  (data.getCount() <= 0) { // (data = null){
            Toast.makeText(
                    this,
                    "Event is empty",
                    Toast.LENGTH_LONG).show();
        }
        mEventAdapter.swapCursor(data);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        mEventAdapter.swapCursor(null);
    }

*/
}

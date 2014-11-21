package com.leobee.animeconvention.manager.manager;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.widget.Filter;
import android.widget.Filterable;

public class AutoCompleteAdapter extends NewsRowAdapter implements Filterable {

	private ArrayList<Item> mData;
	 public AutoCompleteAdapter(Activity act, int resource,
			List<Item> arrayList, List<Integer> disableList) {
		super(act, resource, arrayList, disableList);

        mData = new ArrayList<Item>();
	
		// TODO Auto-generated constructor stub
	}

	

	   
	    

	    @Override
	    public int getCount() {
	        return mData.size();
	    }

	    @Override
	    public Item getItem(int index) {
	        return mData.get(index);
	    }

	    @Override
	    public Filter getFilter() {
	        Filter myFilter = new Filter() {
	            @Override
	            protected FilterResults performFiltering(CharSequence constraint) {
	                FilterResults filterResults = new FilterResults();
	                constraint = constraint.toString().toUpperCase();
	                ArrayList<Item> filt = new ArrayList<Item>();
	                ArrayList<Item>  tmpItems = new ArrayList<Item>();
                   tmpItems.addAll(mData);
                   for(int i = 0; i > tmpItems.size(); i++) {
                       Item sf = tmpItems.get(i);
                       if(sf.getName().toUpperCase().contains(constraint)) {
                       	System.out.println(" FILETERIN RESULTS performFiltering ... " +sf.getName());
                       	
                           filt.add(sf);
                       }

                   }
                       
                       filterResults.count = filt.size();
                       filterResults.values = filt;
                  
	                return filterResults;
	            }

	            @Override
	            protected void publishResults(CharSequence contraint, FilterResults results) {
	                if(results != null && results.count > 0) {
	                notifyDataSetChanged();
	                }
	                else {
	                    notifyDataSetInvalidated();
	                }
	            }
	        };
	        return myFilter;
	    }
	}

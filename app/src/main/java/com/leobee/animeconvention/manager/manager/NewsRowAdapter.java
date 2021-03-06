package com.leobee.animeconvention.manager.manager;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

import com.leobee.animeconvention.manager.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

public class NewsRowAdapter extends ArrayAdapter<Item> {

	private Activity activity;
	private List<Item> items;

	private ArrayList<Item> original;

	private ArrayList<Item> fitems;

	private Filter filter;

	private Item objBean;
	private int row;
	private List<Integer> disable;
	View view ;
	int disableView;

	public NewsRowAdapter(Activity act, int resource, List<Item> arrayList, List<Integer> disableList) {
		super(act, resource, arrayList);
		this.activity = act;
		this.row = resource;
		this.items = arrayList;		
		this.fitems = (ArrayList<Item>) arrayList;
		this.original =(ArrayList<Item>) arrayList;
		this.disable=disableList;

	}
	
	@Override
	public void add(Item item){
	    original.add(item);
	}

	
	public int getCount() {
	    return items.size();        
	}
	public Item getItem(int position) {
	    return items.get(position);
	}
	public long getItemId(int position) {
	    return position;
	}


	    @Override
	    public int getItemViewType(int position) {

       	for(int k =0;k < disable.size();k++){
       		if(position==disable.get(k)){
       			
       			disableView=disable.get(k);
       			
       				
       		} 
   
       	}
          

	return position;
}
  
			@Override
			public View getView(final int position, View convertView, ViewGroup parent) {
				View view = convertView;
				ViewHolder holder;

				if (view == null) {
					LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					view = inflater.inflate(row, null);


					//ViewHolder is a custom class that gets TextViews by name: tvName, tvCity, tvBDate, tvGender, tvAge;
					holder = new ViewHolder();
					
					getItemViewType(position);
					
					if(position==disableView){ 
						System.out.println("disable view value is "+ disableView);
						view.setBackgroundColor(Color.YELLOW);
						if (position==0 ){
							System.out.println("disable view value is "+ disableView);
							view.setBackgroundColor(Color.YELLOW);}
						}else{
							view.setBackgroundColor(Color.WHITE);
							
						
						}
					
					if (position==0 ){
						System.out.println("disable view value is "+ disableView);
					
						Item pot= original.get(position);
						 System.out.println(" delete flag is 0 =" +pot.getDeleteFlag());
						 
						 if(pot.getDeleteFlag()==1){
						view.setBackgroundColor(Color.YELLOW);

					}
						 
						 if(pot.getDeleteFlag()==0){
						view.setBackgroundColor(Color.WHITE);

					}
						 
					}
					
					
					/* setTag Sets the tag associated with this view. A tag can be used to
					 *  mark a view in its hierarchy and does not have to be unique 
					 *  within the hierarchy. Tags can also be used to store data within
					 *   a view without resorting to another data structure.

		*/
					view.setTag(holder);
				} else {
					
					getItemViewType(position);
					
					
					if(position==disableView){ 
						view.setBackgroundColor(Color.YELLOW);
			
						}else{
							view.setBackgroundColor(Color.WHITE);
							}
					
					//the Object stored in this view as a tag
					holder = (ViewHolder) view.getTag();
				}
				 Item item = original.get(position);

				if ((items == null) || ((position + 1) > items.size()))
					return view;

				objBean = items.get(position);


		holder.tv_event_name = (TextView) view.findViewById(R.id.tv_event_name);
		holder.tv_event_date = (TextView) view.findViewById(R.id.tv_event_date);
		holder.tv_event_start = (TextView) view.findViewById(R.id.tv_event_start);
		holder.tv_event_end = (TextView) view.findViewById(R.id.tv_event_end);
		holder.tv_event_location = (TextView) view.findViewById(R.id.tv_event_location);


		if (holder.tv_event_name != null && null != objBean.getName()
				&& objBean.getName().trim().length() > 0) {
			holder.tv_event_name.setText(Html.fromHtml(objBean.getName()));
			
		}
		if (holder.tv_event_date != null && null != objBean.getDate()
				&& objBean.getDate().trim().length() > 0) {
			holder.tv_event_date.setText(Html.fromHtml(objBean.getDate()));
		}
		if (holder.tv_event_start != null && null != objBean.getStartTime()
				&& objBean.getStartTime().trim().length() > 0) {
			holder.tv_event_start.setText(Html.fromHtml(objBean.getStartTime()));
		}
		if (holder.tv_event_end != null && null != objBean.getEndTime()
				&& objBean.getEndTime().trim().length() > 0) {
			holder.tv_event_end.setText(Html.fromHtml(objBean.getEndTime()));
		}
		if (holder.tv_event_location != null && null != objBean.getLocation ()
				&& objBean.getLocation ().trim().length() > 0) {
			holder.tv_event_location.setText(Html.fromHtml(objBean.getLocation ()));
			
		}

		if (position==0 ){
			System.out.println("disable view value is "+ disableView);
		
			Item pot= original.get(position);
			 System.out.println(" delete flag is 0 =" +pot.getDeleteFlag());
			 
			 if(pot.getDeleteFlag()==1){
			view.setBackgroundColor(Color.YELLOW);

		}
			 
			 if(pot.getDeleteFlag()==0){
			view.setBackgroundColor(Color.WHITE);

		}
			 
		}
		return view;
	}

	public class ViewHolder {
		public TextView 
		tv_event_name,
		tv_event_date,
		tv_event_start,
		tv_event_end,
		tv_event_location
		/*tv_event_delete_flag*/;
		
		
	}
	
	
	private class ListFilter extends Filter
	{
	        @Override
	        protected FilterResults performFiltering(CharSequence constraint)
	        {   
	            FilterResults results = new FilterResults();
	            String prefix = constraint.toString().toLowerCase();

	            if (prefix == null || prefix.length() == 0)
	            {
	            	
	            	System.out.println("list is empty");
	            	
	            	 
	                ArrayList<Item> list = new ArrayList<Item>(original);
	                results.values = list;
	                results.count = list.size();
	                
	               
	            }
	            else
	            {
	            	final ArrayList<Item> list = original;

	                int count = list.size();
	                final ArrayList<Item> nlist = new ArrayList<Item>(count);

	                for (int i=0; i<count; i++)
	                {
	                    final Item pkmn = list.get(i);
	                    final String value = pkmn.getName().toLowerCase();

	                    if (value.startsWith(prefix))
	                    {
	                        nlist.add(pkmn);
	                    }
	                }
	                results.values = nlist;
	                results.count = nlist.size();
	            }
	            return results;
	        }

	        @SuppressWarnings("unchecked")
	        @Override
	        protected void publishResults(CharSequence constraint, FilterResults results) {
	            fitems = (ArrayList<Item>)results.values;

	            clear();
	            int count = fitems.size();
	            for (int i=0; i<count; i++)
	            {
	                Item pkmn = (Item)fitems.get(i);
	                add(pkmn);
	            }
	            
	            if (fitems.size() > 0){
	                notifyDataSetChanged();
	            } else{
	                notifyDataSetInvalidated();
	            }
	                        
	        }

	    }

	
	@Override
	public Filter getFilter()
	{
	    if (filter == null)
	        filter = new ListFilter();

	    return filter;
	}

	
}
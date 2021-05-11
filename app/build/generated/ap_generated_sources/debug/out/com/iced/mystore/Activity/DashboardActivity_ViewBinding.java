// Generated code from Butter Knife. Do not modify!
package com.iced.mystore.Activity;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.google.android.gms.ads.AdView;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;
import com.iced.mystore.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class DashboardActivity_ViewBinding implements Unbinder {
  private DashboardActivity target;

  @UiThread
  public DashboardActivity_ViewBinding(DashboardActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public DashboardActivity_ViewBinding(DashboardActivity target, View source) {
    this.target = target;

    target.toolbar = Utils.findRequiredViewAsType(source, R.id.dashboard_toolbar, "field 'toolbar'", MaterialToolbar.class);
    target.cardToday = Utils.findRequiredViewAsType(source, R.id.dashboard_card_today, "field 'cardToday'", MaterialCardView.class);
    target.txtvTodayDate = Utils.findRequiredViewAsType(source, R.id.dashboard_card_today_date, "field 'txtvTodayDate'", TextView.class);
    target.layoutSalesToday = Utils.findRequiredViewAsType(source, R.id.dashboard_card_today_sales_layout, "field 'layoutSalesToday'", RelativeLayout.class);
    target.txtvSalesToday = Utils.findRequiredViewAsType(source, R.id.dashboard_card_today_sales_prompt, "field 'txtvSalesToday'", TextView.class);
    target.layoutExpensesToday = Utils.findRequiredViewAsType(source, R.id.dashboard_card_today_expenses_layout, "field 'layoutExpensesToday'", RelativeLayout.class);
    target.txtvExpensesToday = Utils.findRequiredViewAsType(source, R.id.dashboard_card_today_expenses_prompt, "field 'txtvExpensesToday'", TextView.class);
    target.layoutTotal = Utils.findRequiredViewAsType(source, R.id.dashboard_card_today_total_layout, "field 'layoutTotal'", RelativeLayout.class);
    target.txtvTotalToday = Utils.findRequiredViewAsType(source, R.id.dashboard_card_today_total_prompt, "field 'txtvTotalToday'", TextView.class);
    target.cardLastOrder = Utils.findRequiredViewAsType(source, R.id.dashboard_card_last_order, "field 'cardLastOrder'", MaterialCardView.class);
    target.txtvLastOrderNumber = Utils.findRequiredViewAsType(source, R.id.dashboard_card_last_order_order_number_txtv, "field 'txtvLastOrderNumber'", TextView.class);
    target.txtvLasOrderDate = Utils.findRequiredViewAsType(source, R.id.dashboard_card_last_order_date_txtv, "field 'txtvLasOrderDate'", TextView.class);
    target.layoutLastOrderCost = Utils.findRequiredViewAsType(source, R.id.dashboard_card_last_Order_cost_layout, "field 'layoutLastOrderCost'", RelativeLayout.class);
    target.btnViewOrder = Utils.findRequiredViewAsType(source, R.id.dashboard_card_last_order_view_btn, "field 'btnViewOrder'", Button.class);
    target.txtvLastOrderCost = Utils.findRequiredViewAsType(source, R.id.dashboard_card_last_order_cost_prompt, "field 'txtvLastOrderCost'", TextView.class);
    target.layoutProductList = Utils.findRequiredViewAsType(source, R.id.dashboard_product_list_layout, "field 'layoutProductList'", LinearLayout.class);
    target.recyclerViewProductList = Utils.findRequiredViewAsType(source, R.id.dashboard_card_product_list_recyclerview, "field 'recyclerViewProductList'", RecyclerView.class);
    target.layouEmptyProductList = Utils.findRequiredViewAsType(source, R.id.dashboard_product_empty_layout, "field 'layouEmptyProductList'", LinearLayout.class);
    target.btnAddNew = Utils.findRequiredViewAsType(source, R.id.dashboard_product_empty_create_btn, "field 'btnAddNew'", Button.class);
    target.scrollView = Utils.findRequiredViewAsType(source, R.id.dashboard_scrollview, "field 'scrollView'", NestedScrollView.class);
    target.bannerAd = Utils.findRequiredViewAsType(source, R.id.dashboard_adview, "field 'bannerAd'", AdView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    DashboardActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.cardToday = null;
    target.txtvTodayDate = null;
    target.layoutSalesToday = null;
    target.txtvSalesToday = null;
    target.layoutExpensesToday = null;
    target.txtvExpensesToday = null;
    target.layoutTotal = null;
    target.txtvTotalToday = null;
    target.cardLastOrder = null;
    target.txtvLastOrderNumber = null;
    target.txtvLasOrderDate = null;
    target.layoutLastOrderCost = null;
    target.btnViewOrder = null;
    target.txtvLastOrderCost = null;
    target.layoutProductList = null;
    target.recyclerViewProductList = null;
    target.layouEmptyProductList = null;
    target.btnAddNew = null;
    target.scrollView = null;
    target.bannerAd = null;
  }
}

// Generated code from Butter Knife. Do not modify!
package com.iced.mystore.Activity;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputLayout;
import com.iced.mystore.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class RestockActivity_ViewBinding implements Unbinder {
  private RestockActivity target;

  @UiThread
  public RestockActivity_ViewBinding(RestockActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public RestockActivity_ViewBinding(RestockActivity target, View source) {
    this.target = target;

    target.toolbar = Utils.findRequiredViewAsType(source, R.id.restock_toolbar, "field 'toolbar'", MaterialToolbar.class);
    target.nestedListLayout = Utils.findRequiredViewAsType(source, R.id.restock_list_layout, "field 'nestedListLayout'", RelativeLayout.class);
    target.txtvDate = Utils.findRequiredViewAsType(source, R.id.restock_date_txtv, "field 'txtvDate'", TextView.class);
    target.txtvTotal = Utils.findRequiredViewAsType(source, R.id.restock_total_txtv, "field 'txtvTotal'", TextView.class);
    target.btnPay = Utils.findRequiredViewAsType(source, R.id.restock_btn_pay, "field 'btnPay'", Button.class);
    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.restock_recyclerview, "field 'recyclerView'", RecyclerView.class);
    target.emptyListLayout = Utils.findRequiredViewAsType(source, R.id.restock_empty_layout, "field 'emptyListLayout'", LinearLayout.class);
    target.btnClose = Utils.findRequiredViewAsType(source, R.id.restock_bottomsheet_close_btn, "field 'btnClose'", Button.class);
    target.tilProduct = Utils.findRequiredViewAsType(source, R.id.restock_bottomsheet_product_til_dropdown, "field 'tilProduct'", TextInputLayout.class);
    target.tilCost = Utils.findRequiredViewAsType(source, R.id.restock_bottomsheet_cost_til, "field 'tilCost'", TextInputLayout.class);
    target.tilCount = Utils.findRequiredViewAsType(source, R.id.restock_bottomsheet_count_til, "field 'tilCount'", TextInputLayout.class);
    target.btnAddRestock = Utils.findRequiredViewAsType(source, R.id.restock_bottomsheet_add_restock_btn, "field 'btnAddRestock'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    RestockActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.nestedListLayout = null;
    target.txtvDate = null;
    target.txtvTotal = null;
    target.btnPay = null;
    target.recyclerView = null;
    target.emptyListLayout = null;
    target.btnClose = null;
    target.tilProduct = null;
    target.tilCost = null;
    target.tilCount = null;
    target.btnAddRestock = null;
  }
}

// Generated code from Butter Knife. Do not modify!
package com.iced.mystore.Activity;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.google.android.material.appbar.MaterialToolbar;
import com.iced.mystore.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ViewOrderActivity_ViewBinding implements Unbinder {
  private ViewOrderActivity target;

  @UiThread
  public ViewOrderActivity_ViewBinding(ViewOrderActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ViewOrderActivity_ViewBinding(ViewOrderActivity target, View source) {
    this.target = target;

    target.toolbar = Utils.findRequiredViewAsType(source, R.id.view_order_toolbar, "field 'toolbar'", MaterialToolbar.class);
    target.txtvOrderNumber = Utils.findRequiredViewAsType(source, R.id.view_order_txtv_ordernumber, "field 'txtvOrderNumber'", TextView.class);
    target.txtvDate = Utils.findRequiredViewAsType(source, R.id.view_order_txtv_date, "field 'txtvDate'", TextView.class);
    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.view_order_recyclerview, "field 'recyclerView'", RecyclerView.class);
    target.txtvCosts = Utils.findRequiredViewAsType(source, R.id.view_order_txtv_costs, "field 'txtvCosts'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ViewOrderActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.txtvOrderNumber = null;
    target.txtvDate = null;
    target.recyclerView = null;
    target.txtvCosts = null;
  }
}

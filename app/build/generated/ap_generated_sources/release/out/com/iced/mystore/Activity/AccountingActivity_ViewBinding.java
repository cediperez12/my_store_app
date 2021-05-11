// Generated code from Butter Knife. Do not modify!
package com.iced.mystore.Activity;

import android.view.View;
import android.widget.Button;
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

public class AccountingActivity_ViewBinding implements Unbinder {
  private AccountingActivity target;

  @UiThread
  public AccountingActivity_ViewBinding(AccountingActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public AccountingActivity_ViewBinding(AccountingActivity target, View source) {
    this.target = target;

    target.toolbar = Utils.findRequiredViewAsType(source, R.id.accounting_toolbar, "field 'toolbar'", MaterialToolbar.class);
    target.txtvDate = Utils.findRequiredViewAsType(source, R.id.accounting_date_txtv, "field 'txtvDate'", TextView.class);
    target.txtvSales = Utils.findRequiredViewAsType(source, R.id.accounting_sales_txtv, "field 'txtvSales'", TextView.class);
    target.txtvExpenses = Utils.findRequiredViewAsType(source, R.id.accounting_expenses_txtv, "field 'txtvExpenses'", TextView.class);
    target.txtvRevenue = Utils.findRequiredViewAsType(source, R.id.accounting_revenue_txtv, "field 'txtvRevenue'", TextView.class);
    target.btnChange = Utils.findRequiredViewAsType(source, R.id.accounting_btn_change_date, "field 'btnChange'", Button.class);
    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.accounting_recyclerview, "field 'recyclerView'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AccountingActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.txtvDate = null;
    target.txtvSales = null;
    target.txtvExpenses = null;
    target.txtvRevenue = null;
    target.btnChange = null;
    target.recyclerView = null;
  }
}

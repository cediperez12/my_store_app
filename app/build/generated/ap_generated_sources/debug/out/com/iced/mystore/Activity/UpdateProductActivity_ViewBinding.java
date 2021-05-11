// Generated code from Butter Knife. Do not modify!
package com.iced.mystore.Activity;

import android.view.View;
import android.widget.Button;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputLayout;
import com.iced.mystore.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class UpdateProductActivity_ViewBinding implements Unbinder {
  private UpdateProductActivity target;

  @UiThread
  public UpdateProductActivity_ViewBinding(UpdateProductActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public UpdateProductActivity_ViewBinding(UpdateProductActivity target, View source) {
    this.target = target;

    target.toolbar = Utils.findRequiredViewAsType(source, R.id.update_product_toolbar, "field 'toolbar'", MaterialToolbar.class);
    target.tilProdName = Utils.findRequiredViewAsType(source, R.id.update_product_til_prod_name, "field 'tilProdName'", TextInputLayout.class);
    target.tilProdCost = Utils.findRequiredViewAsType(source, R.id.update_product_til_prod_cost, "field 'tilProdCost'", TextInputLayout.class);
    target.btnSave = Utils.findRequiredViewAsType(source, R.id.update_product_btn_save, "field 'btnSave'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    UpdateProductActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.tilProdName = null;
    target.tilProdCost = null;
    target.btnSave = null;
  }
}

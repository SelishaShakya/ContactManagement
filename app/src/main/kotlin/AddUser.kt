import android.content.Intent
import android.support.v7.widget.Toolbar
import android.widget.EditText
import android.widget.Toast

import com.project.contactmanagement.R
import com.project.contactmanagement.adduser.model.UserModel
import com.project.contactmanagement.adduser.presenter.AddUserImplementor
import com.project.contactmanagement.adduser.presenter.AddUserPresenter
import com.project.contactmanagement.base.BaseActivity
import com.project.contactmanagement.retriveuser.view.UserList

import butterknife.BindView
import butterknife.OnClick
import io.realm.Realm

import com.project.contactmanagement.utils.TextViewUtils.isEmailValid

/**
 * Created by Own on 4/11/2018.
 */

class AddUser : BaseActivity(), AddUserView {

    internal var addUserPresenter: AddUserPresenter? = null
    @BindView(R.id.et_add_user_full_name)
    internal var etFullName: EditText? = null
    @BindView(R.id.et_add_user_address)
    internal var etAddress: EditText? = null
    @BindView(R.id.et_add_user_contact)
    internal var etContact: EditText? = null
    @BindView(R.id.et_add_user_email)
    internal var etEmail: EditText? = null
    @BindView(R.id.toolbar)
    internal var toolbar: Toolbar? = null
    internal var mRealm: Realm?= Realm.getDefaultInstance()

    override fun getLayout(): Int {
        return R.layout.add_user
    }

    override fun init() {
        addUserPresenter = AddUserImplementor(this, this)
    }


    @OnClick(R.id.btn_submit)
    fun addUser() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setTitle(getString(R.string.add_user))
        val fullName = etFullName!!.text.toString()
        val address = etAddress!!.text.toString()
        val contact = etContact!!.text.toString()
        val email = etEmail!!.text.toString()

        if (fullName.isBlank()) {
            etFullName!!.error = "Required"
            etFullName!!.requestFocus()
        } else if (address.isBlank()) {
            etAddress!!.error = "Required"
            etAddress!!.requestFocus()
        } else if (contact.isBlank()) {
            etContact!!.error = "Required"
            etContact!!.requestFocus()
        } else if (email.isBlank()) {
            etEmail!!.error = "Required"
            etEmail!!.requestFocus()
        } else if (!isEmailValid(email)) {
            etEmail!!.error = "Invalid Email"
            etEmail!!.requestFocus()
        } else {
            val userModel = UserModel(fullName, address, email, contact)
            addUserPresenter.addUser(userModel)
        }
    }


    @OnClick(R.id.btn_user_list)
    fun openUserList() {
        val i = Intent(this, UserList::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(i)
    }

    override fun addSuccess() {
        Toast.makeText(this, "User added", Toast.LENGTH_SHORT).show()
        val i = Intent(this, UserList::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(i)
    }

    override fun errorAdding() {
        Toast.makeText(this, "Could not add", Toast.LENGTH_SHORT).show()

    }
}

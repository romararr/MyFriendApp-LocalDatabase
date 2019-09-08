package ezy.id.myfriendapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.my_friends_add_fragment.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MyFriendsAddFragment : Fragment() {

    companion object {
        fun newInstance(): MyFriendsAddFragment {
            return MyFriendsAddFragment()
        }
    }

    private var db: AppDatabase? = null
    private var myFriendDao: MyFriendDAO? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.my_friends_add_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLocalDB()
        initView()
    }

    private fun initLocalDB() {
        db = AppDatabase.getAppDatabase(activity!!)
        myFriendDao = db?.myFriendDao()
    }

    private fun initView() {
        btnSave.setOnClickListener {
            (activity as MainActivity).tampilMyFriendsFragment()
        }

        setDataSpinnerGender()
    }

    private fun setDataSpinnerGender() {
        val adapter = ArrayAdapter.createFromResource(activity!!, R.array.gender_list,
            android.R.layout.simple_spinner_item)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerGender.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        this.clearFindViewByIdCache()
    }

    private fun validasiInput(){
        val namaInput = edtName?.text.toString()
        val emailInput = edtEmail?.text.toString()
        val telpInput = edtTelp?.text.toString()
        val alamatInput = edtAddress?.text.toString()
        val genderInput = spinnerGender?.selectedItem.toString()

        when {

            namaInput.isEmpty() -> edtName?.error = "Nama tidak boleh kosong"
            genderInput.equals("Pilih Kelamin") -> Toast.makeText(context,"Kelamin harus dipilih", Toast.LENGTH_SHORT).show()
            emailInput.isEmpty() -> edtEmail?.error = "Email tidak boleh kosong"
            telpInput.isEmpty() -> edtTelp?.error = "Telp tidak boleh kosong"
            alamatInput.isEmpty() -> edtAddress?.error = "Alamat tidak boleh kosong"

            else -> {

                val teman = MyFriend(nama=namaInput, kelamin = genderInput,
                    email = emailInput, telp = telpInput, alamat = alamatInput)
                tambahDataTeman(teman)

            }

        }
    }

    private fun tambahDataTeman(teman: MyFriend): Job {
        return GlobalScope.launch {
            myFriendDao?.tambahTeman(teman)
            (activity as MainActivity).tampilMyFriendsFragment()
        }
    }

}
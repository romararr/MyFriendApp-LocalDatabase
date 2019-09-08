package ezy.id.myfriendapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.my_friends_fragment.*

class MyFriendsFragment : Fragment() {

    //    lateinit var listTeman: MutableList<MyFriend>
    private var listTeman: List<MyFriend>? = null
    private var db: AppDatabase? = null
    private var myFriendDao: MyFriendDAO? = null

    companion object {
        fun newInstance(): MyFriendsFragment {
            return MyFriendsFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.my_friends_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        intitLocalDB()
    }

    private fun intitLocalDB() {
        db = AppDatabase.getAppDatabase(activity!!)
        myFriendDao = db?.myFriendDao()
    }

    private fun initView() {
        fabAddFriend.setOnClickListener {
            (activity as MainActivity).tampilMyFriendsAddFragment()
        }

//        simulasiDataTeman()
//        tampilTeman()
        ambilDataTeman()
    }

    private fun ambilDataTeman() {
        listTeman = ArrayList()
        myFriendDao?.ambilSemuaTeman()?.observe(this, Observer { result ->
            listTeman = result

            when {
                listTeman?.size === 0 -> Toast.makeText(
                    context,
                    "Belum Ada Teman",
                    Toast.LENGTH_SHORT
                ).show()

                else -> tampilTeman()
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        this.clearFindViewByIdCache()
    }

    private fun simulasiDataTeman() {
//        listTeman = ArrayList()
//
//        listTeman.add(MyFriend("Rama Romara", "rama@romara.com",
//            "083851525000", "Laki-Laki", "Jalan dimana aku dan kamu dipertemukan"))
//        listTeman.add(MyFriend("Abigocia Cralihar", "abi@cralihar.com",
//            "081935880992", "Perempuan", "Jalanin aja dulu siapa tau jodoh"))

    }

    private fun tampilTeman() {
        listMyFriends.layoutManager = LinearLayoutManager(activity)
        listMyFriends.adapter = MyFriendAdapter(
            activity!!,
            listTeman as ArrayList<MyFriend>
        )
    }

}
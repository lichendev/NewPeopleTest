package com.example.newpeopletest2

import android.content.ContentValues
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BlankFragment1.newInstance] factory method to
 * create an instance of this fragment.
 */
class BlankFragment1 : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    lateinit var myAdapter: MyAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if(context!=null) {
            val view = inflater.inflate(R.layout.fragment_blank1, container, false)
            val recyclerView = view.findViewById<RecyclerView>(R.id.frag1_recycle_view)
            recyclerView.layoutManager = LinearLayoutManager(activity)
            val oldData = recoverData()
            myAdapter = MyAdapter(requireContext(), oldData)
            recyclerView.adapter = myAdapter
            val button = view.findViewById<Button>(R.id.frag1_button)
            val editText = view.findViewById<EditText>(R.id.frag1_edit_text)
            button.setOnClickListener {
                myAdapter.addData(editText.text.toString())
                editText.setText("".toCharArray(),0,0)
            }
            return view
        }else
            return null
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onPause() {
        storeData()
        super.onPause()
    }

    fun storeData(){
        val db = StrDatabaseHelper(requireContext(),"Frag1.db",1).writableDatabase
        val newData = myAdapter.getNewStrs()
        db.let { it ->
            for(str in newData){
                val contentValue = ContentValues()
                contentValue.put("value",str)
                val calendar = Calendar.getInstance()
                contentValue.put("year",calendar.get(Calendar.YEAR))
                contentValue.put("month",calendar.get(Calendar.MONTH))
                contentValue.put("day",calendar.get(Calendar.DATE))
                it.insert("stringt",null,contentValue)
            }
        }
    }

    fun recoverData(): List<String>{
        val db = StrDatabaseHelper(requireContext(),"Frag1.db",1).readableDatabase
        val data = ArrayList<String>()
        val calendar = Calendar.getInstance()
        val cursor = db.query("stringt", arrayOf("value"),"year=? and month=? and day=?",
            arrayOf(calendar.get(Calendar.YEAR).toString(), calendar.get(Calendar.MONTH).toString(),calendar.get(Calendar.DATE).toString()
            ),null,null,"id")
        while (cursor.moveToNext()){
            data.add(cursor.getString(cursor.getColumnIndex("value")))
        }
        return data
    }

    class MyAdapter(private val context: Context, initData: List<String>?): RecyclerView.Adapter<MyAdapter.ViewHolder>(){

         val data = ArrayList<String>()
         private var newStrIndex = 0

         init {
             initData?.let {
                 data.addAll(it)
                 newStrIndex = data.size
             }
         }

         fun addData(mess: String){
             data.add(mess)
             notifyDataSetChanged()
         }

        fun getNewStrs(): List<String>{
            val oldIndex = newStrIndex
            newStrIndex = data.size
            return data.subList(oldIndex,data.size)//newStrIndex=data.size

        }

         class ViewHolder(view: View): RecyclerView.ViewHolder(view){
            val textView = view.findViewById<TextView>(R.id.recycle_item_text)
         }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view=LayoutInflater.from(context).inflate(R.layout.recycler_item,null)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.textView.text = data[position]
        }

        override fun getItemCount(): Int {
            return data.size
        }
    }

}
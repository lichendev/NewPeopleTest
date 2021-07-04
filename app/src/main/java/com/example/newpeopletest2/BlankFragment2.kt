package com.example.newpeopletest2

import android.content.Context
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
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BlankFragment2.newInstance] factory method to
 * create an instance of this fragment.
 */
class BlankFragment2 : Fragment() {

    lateinit var adapter: FileAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_blank2, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.frag2_recycle_view)
        val editText = view.findViewById<EditText>(R.id.frag2_file_path)
        val searchButton = view.findViewById<Button>(R.id.frag2_search)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        editText.setText("/storage/emulated/0".toCharArray(),0,19)
        adapter = FileAdapter(requireContext(),File("/storage/emulated/0").listFiles().toList())
        recyclerView.adapter = adapter
        searchButton.setOnClickListener {
            val path = editText.text.toString()
            adapter.updateData(File(path).listFiles().toList())
        }
        return view
    }

}

class FileAdapter(private val context: Context, var data: List<File>): RecyclerView.Adapter<FileAdapter.ViewHolder>(){

    val sd = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    fun updateData(newData: List<File>){
        data=newData
        notifyDataSetChanged()
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val name = view.findViewById<TextView>(R.id.frag2_recycle_item_name)
        val time = view.findViewById<TextView>(R.id.frag2_recycle_item_time)
        val type = view.findViewById<TextView>(R.id.frag2_recycle_item_type)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(context).inflate(R.layout.frac2_recycler_item,null)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val file = data[position]
        holder.name.text = file.name
        holder.time.text = sd.format(Date(file.lastModified()))
        if(file.isDirectory)
            holder.type.text = "DIR"
        else
            holder.type.text = "FILE"
    }

    override fun getItemCount(): Int {
        return data.size
    }
}
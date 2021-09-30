package com.internshala.workshop.fragments

import android.os.Bundle
import android.os.WorkSource
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.internshala.database.helpers.DBHelper
import com.internshala.database.models.Workshop
import com.internshala.workshop.R
import com.internshala.workshop.adapters.WorkshopRecyclerAdapter

class WorkshopFrag : Fragment() {

    private lateinit var workshopRecycler: RecyclerView

    private lateinit var workshopRecyclerAdapter: WorkshopRecyclerAdapter

    private lateinit var myDbHelper: DBHelper
    private var workshopList: MutableList<Workshop>? = null
    private var enrolledList: MutableList<Workshop>? = null
    private var displayList: MutableList<Workshop>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_workshop, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        workshopRecycler = view.findViewById(R.id.workshop_recycler)

        myDbHelper = DBHelper(requireActivity())

        workshopList = myDbHelper.getAllWorkshops()?:mutableListOf()

        workshopRecycler.apply {
            setHasFixedSize(true)
            val recyclerLayoutManager = LinearLayoutManager(requireActivity())
            recyclerLayoutManager.orientation = RecyclerView.VERTICAL
            layoutManager = recyclerLayoutManager
            adapter = WorkshopRecyclerAdapter(requireActivity(), workshopList?:mutableListOf())
        }

    }

}
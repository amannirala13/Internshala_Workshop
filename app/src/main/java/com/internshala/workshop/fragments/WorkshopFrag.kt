package com.internshala.workshop.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.internshala.database.helpers.DBHelper
import com.internshala.database.models.Workshop
import com.internshala.utils.getCurrentUserID
import com.internshala.workshop.R
import com.internshala.workshop.adapters.WorkshopRecyclerAdapter

/**
 *  This fragment displays all the available workshops. Everyone can access this list but has to
 *  authenticate to enroll in a particular workshop.
 *
 * @property workshopRecycler RecyclerView
 * @property workshopRecyclerAdapter WorkshopRecyclerAdapter
 * @property myDbHelper DBHelper
 * @property workshopList MutableList<Workshop>?
 * @property enrolledList MutableList<Workshop>?
 */
class WorkshopFrag : Fragment() {

    private lateinit var workshopRecycler: RecyclerView

    private lateinit var workshopRecyclerAdapter: WorkshopRecyclerAdapter

    private lateinit var myDbHelper: DBHelper
    private var workshopList: MutableList<Workshop>? = null
    private var enrolledList: MutableList<Workshop>? = null

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
        enrolledList = myDbHelper.getStudentEnrollments(getCurrentUserID(requireActivity()))?:mutableListOf()

        workshopList?.sortBy{it.id}
        enrolledList?.sortBy{it.id}

        workshopRecycler.apply {
            setHasFixedSize(true)
            val recyclerLayoutManager = LinearLayoutManager(requireActivity())
            recyclerLayoutManager.orientation = RecyclerView.VERTICAL
            layoutManager = recyclerLayoutManager
            adapter = WorkshopRecyclerAdapter(requireActivity(),
                workshopList?:mutableListOf(),
                enrolledList?:mutableListOf())
        }

    }

}
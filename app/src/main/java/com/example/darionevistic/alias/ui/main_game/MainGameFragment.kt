package com.example.darionevistic.alias.ui.main_game

import android.app.Fragment
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.darionevistic.alias.R
import javax.inject.Inject


/**
 * Created by dario.nevistic on 21/03/2018.
 */
class MainGameFragment : Fragment(), MainGameContract.View {

    @Inject
    lateinit var presenter: MainGamePresenter

    companion object {
        fun newInstance() =
                MainGameFragment()
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater?.inflate(R.layout.fragment_main_game, container, false)

        val dialogBuilder = AlertDialog.Builder(activity)
// ...Irrelevant code for customizing the buttons and title
        val inf = activity.layoutInflater
        val dialogView = inf.inflate(R.layout.dialog_get_ready, null)
        dialogBuilder.setView(dialogView)

        val alertDialog = dialogBuilder.create()
        alertDialog.show()


        return root
    }


}
package com.mauroalexandro.cookpadmobile.utils

import androidx.fragment.app.FragmentActivity
import com.mauroalexandro.cookpadmobile.ui.collections.CollectionDetailFragment

/**
 * Created by Mauro_Chegancas
 */
class Utils {

    companion object {
        private var INSTANCE: Utils? = null
        fun getInstance(): Utils {
            if (INSTANCE == null)
                INSTANCE = Utils()

            return INSTANCE as Utils
        }
    }

    /**
     * Call CollectionDetailsFragment
     */
    fun openCollectionDetailsFragment(activity: FragmentActivity, collectionID: Int) {
        val collectionDetailFragment = CollectionDetailFragment(collectionID)
        if(!activity.supportFragmentManager.isDestroyed)
            collectionDetailFragment.show(
                activity.supportFragmentManager,
                "collection_detail_dialog_fragment"
            )
    }
}
package com.mauroalexandro.cookpadmobile.adapters

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mauroalexandro.cookpadmobile.R
import com.mauroalexandro.cookpadmobile.models.Step
import java.lang.StringBuilder

/**
 * Created by Mauro_Chegancas
 */
class StepsRecyclerViewAdapter(
    private val context: Context,
    private var stepsList: List<Step>
) : RecyclerView.Adapter<StepsRecyclerViewAdapter.StepsViewHolder>() {
    //private var stepsList = arrayListOf<Step>()
    //private var utils: Utils = Utils.getInstance()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepsViewHolder {
        return StepsViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.steps_row,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(stepsViewHolder: StepsViewHolder, position: Int) {
        //Step
        val step: Step = getStepList(position)

        //Step number
        val stringBuilder = StringBuilder()
        val stepNumberText = stringBuilder.append(position+1).append(" . ")
        stepsViewHolder.stepNumber.text = stepNumberText

        //Step Description
        stepsViewHolder.stepDescription.text = step.description
        stepsViewHolder.stepDescription.textAlignment = TextView.TEXT_ALIGNMENT_TEXT_START
        stepsViewHolder.stepDescription.typeface  = Typeface.DEFAULT

        //Step Image
        if(step.image_urls.isNotEmpty())
            Glide
                .with(context)
                .load(step.image_urls[0])
                .fitCenter()
                //.apply(RequestOptions().override(500, 500))
                .into(stepsViewHolder.stepImage)

        //Collection Recipes Count
       /* val recipesCountText = context.resources.getString(R.string.recipes_count_text,step.recipe_count.toString())
        collectionsViewHolder.collectionRecipesCount.text = recipesCountText

        //Collections Image List
        val collectionsImageAdapter = CollectionsImageAdapter(context, step.preview_image_urls)
        collectionsViewHolder.collectionImageList.adapter = collectionsImageAdapter
        collectionsViewHolder.collectionImageList.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )*/
    }

    override fun getItemCount(): Int {
        return stepsList.size
    }

    private fun getStepList(position: Int): Step {
        return stepsList[position]
    }

    class StepsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val stepNumber: TextView = view.findViewById(R.id.steps_row_number)
        val stepDescription: TextView = view.findViewById(R.id.steps_row_description)
        val stepImage: ImageView = view.findViewById(R.id.steps_row_Image)
    }
}
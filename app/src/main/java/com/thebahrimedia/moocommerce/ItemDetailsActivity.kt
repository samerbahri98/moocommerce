package com.thebahrimedia.moocommerce

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.thebahrimedia.moocommerce.adapter.ShoppingItemAdapter
import com.thebahrimedia.moocommerce.databinding.ActivityItemDetailsBinding
import com.thebahrimedia.moocommerce.db.Item
import android.widget.TextView
import com.thebahrimedia.moocommerce.api.MoneyPayload
import com.thebahrimedia.moocommerce.api.MoneyRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ItemDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityItemDetailsBinding
    private var retrofit : Retrofit = Retrofit.Builder()
        .baseUrl("https://api.frankfurter.app/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private  var moneyRequest: MoneyRequest = retrofit.create(MoneyRequest::class.java)
    private val rateCall = moneyRequest.getRates("HUF")

    lateinit var itemAdapter: ShoppingItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.topReturnBar.setNavigationOnClickListener(cancelListener)

        var item:Item = intent.getSerializableExtra("itemId") as Item

        binding.tvName.text =item.name
        binding.tvDescription.text= item.description
        val categoryUri = "@drawable/"+item.category
        val imageResource = resources.getIdentifier(categoryUri,null,packageName)
        binding.ivCategory.setImageResource(imageResource)

        binding.tvPriceHuf.text = item.Price.toString() + " HUF"
        convertPrices(binding.tvPriceEur,binding.tvPriceAud,binding.tvPriceJpy,item.Price)



        if (item.isPurchased){
            binding.isPurchased.text = "is Purchased"
        }
        else{
            binding.isPurchased.text = "is Not Purchased"
        }

    }

    private fun convertPrices(tvEur:TextView,tvAud:TextView,tvJpy:TextView,price:Float){

        rateCall.enqueue(object :Callback<MoneyPayload>{
            override fun onResponse(call: Call<MoneyPayload>, response: Response<MoneyPayload>) {
                var eurRate: Float? = response.body()?.rates?.EUR?.toFloat()
                val eurConverted: Float = String.format("%.3f", eurRate!! * price).toFloat() ;
                tvEur.text = eurConverted.toString() + " EUR"


                var audRate: Float? = response.body()?.rates?.AUD?.toFloat()
                val audConverted: Float = String.format("%.3f", audRate!! * price).toFloat() ;
                tvAud.text = audConverted.toString() + " AUD"


                var jpyRate: Float? = response.body()?.rates?.JPY?.toFloat()
                val jpyConverted: Float = String.format("%.3f", jpyRate!! * price).toFloat() ;
                tvJpy.text = jpyConverted.toString() + " JPY"
            }

            override fun onFailure(call: Call<MoneyPayload>, t: Throwable) {
                Log.e("API error",t.message!!)
            }

        })

    }



    private val cancelListener = View.OnClickListener {
        finish()
    }
}
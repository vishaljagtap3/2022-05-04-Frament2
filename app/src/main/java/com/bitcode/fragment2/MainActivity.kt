package com.bitcode.fragment2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.bitcode.fragment2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var counterFragment : CounterFragment
    private lateinit var binding: ActivityMainBinding

    private var counterFragmentsList = ArrayList<CounterFragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initFragments()
        initListeners()
    }

    private fun initListeners() {
        binding.btnResetCount.setOnClickListener {
            counterFragment.count = binding.edtCount.text.toString().toInt()

            for(counterFragment in counterFragmentsList) {
                counterFragment.count = binding.edtCount.text.toString().toInt()
                //counterFragment.title = binding.edtCounterFragmentTitle.text.toString()
            }
        }

        binding.btnAddCounterFragment.setOnClickListener {
            //code to add counter fragment
            val counterFragment = CounterFragment()

            //counterFragment.title = binding.edtCount.text.toString()
            // will not work here as the view i.e. binding object is not initialized yet
            //cause the onCreateView method is not called at this point of time.. it will be called when
            //you add the fragment to the view hierarchy of the activity

            //provide input to the fragment
            var input = Bundle()
            input.putString("title", binding.edtCounterFragmentTitle.text.toString())
            input.putInt("count", binding.edtCount.text.toString().toInt())
            counterFragment.arguments = input

            var fragmentTransaction = supportFragmentManager.beginTransaction()
            //fragmentTransaction.add(counterFragment, "counter_fragment")
            fragmentTransaction.add(R.id.mainContainer, counterFragment, null)
            fragmentTransaction.commit()


            counterFragmentsList.add(counterFragment)

        }

        binding.btnRemoveCounterFragment.setOnClickListener {

            if(counterFragmentsList.size == 0) {
                return@setOnClickListener
            }

            //code to remove counter fragment
            supportFragmentManager.beginTransaction()
                .remove(counterFragmentsList[0])
                .commit()

            counterFragmentsList.removeAt(0)
        }
    }

    private fun initFragments() {
        counterFragment = supportFragmentManager.findFragmentById(R.id.counterFragment) as CounterFragment
    }

    override fun onStart() {
        super.onStart()
        counterFragment.count = 0
    }
}
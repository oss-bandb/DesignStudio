package dev.bandb.designstudio

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import dev.bandb.designstudio.databinding.MainActivityBinding
import dev.bandb.designstudio.databinding.SampleItemBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.sampleList.apply {
            adapter = SampleAdapter(Samples.data)
        }
    }
}

class SampleAdapter(private val samples: List<Samples.DesignSample>) :
    RecyclerView.Adapter<SampleViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SampleViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return SampleViewHolder(SampleItemBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: SampleViewHolder, position: Int) {
        holder.bind(samples[position])
    }

    override fun getItemCount(): Int = samples.size

}

class SampleViewHolder(private val binding: SampleItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    private val context = binding.root.context

    fun bind(sample: Samples.DesignSample) {
        binding.showSample.text = sample.title
        binding.showSample.setOnClickListener {
            ContextCompat.startActivity(context, Intent(context, sample.clazz.java), null)
        }
    }
}
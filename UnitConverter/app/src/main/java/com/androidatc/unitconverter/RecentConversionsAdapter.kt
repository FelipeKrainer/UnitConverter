import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.androidatc.unitconverter.ConversionEntry
import com.androidatc.unitconverter.R

class RecentConversionsAdapter(private val conversions: List<ConversionEntry>) :
    RecyclerView.Adapter<RecentConversionsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val fromToTextView: TextView = view.findViewById(R.id.fromToTextView)
        val resultTextView: TextView = view.findViewById(R.id.resultTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_conversion, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val conversion = conversions[position]
        holder.resultTextView.text = "Result: ${conversion.fromUnit} to ${conversion.toUnit} \u27A1 ${conversion.result}"
    }



    override fun getItemCount(): Int {
        return conversions.size
    }
}


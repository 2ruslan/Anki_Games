package rock.ankigames.ViewCardsGame;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import rock.ankigames.Anki.NoteInfo;
import rock.ankigames.R;

public class NoteInfoAdapter extends ArrayAdapter<NoteInfo> {

    private LayoutInflater _inflater;
    private int _layout;
    private ArrayList<NoteInfo> _notes;
    Context _context;

    NoteInfoAdapter(Context context, int resource, ArrayList<NoteInfo> notes) {
        super(context, resource, notes);

        _context = context;

        _notes = notes;
        _layout = resource;
        _inflater = LayoutInflater.from(context);
    }


    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;
        if(convertView==null){
            convertView = _inflater.inflate(this._layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final NoteInfo note = _notes.get(position);

        viewHolder.tvQuestion.setText(note.getQuestion());
        viewHolder.tvAnswer.setText(note.getAnswer());

        //viewHolder.SettingsButton.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
//                product.OpenSettings();
       //     }
        //}
        //);

        return convertView;
    }

    private class ViewHolder {
        final TextView tvQuestion, tvAnswer;
        ViewHolder(View view){

            tvQuestion = view.findViewById(R.id.tvQuestion);
            tvAnswer = view.findViewById(R.id.tvAnswer);
        }
    }

}
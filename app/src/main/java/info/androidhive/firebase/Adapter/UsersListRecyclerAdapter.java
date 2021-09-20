package info.androidhive.firebase.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import butterknife.ButterKnife;
import info.androidhive.firebase.Model.FormData;
import info.androidhive.firebase.R;

public class UsersListRecyclerAdapter
        extends BaseRecyclerAdapter<UsersListRecyclerAdapter.UsersLisViewHolder> {

    private List<FormData> rowList;
    TextView name;
    TextView mobileNumber;
    TextView email;
    TextView bankName;
    TextView branchName;
    TextView accountNumber;
    TextView availablePoints;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReferenceForPoints;

    class UsersLisViewHolder  extends RecyclerView.ViewHolder {

        UsersLisViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(UsersLisViewHolder.this,itemView);
        }
    }

    public UsersListRecyclerAdapter(List<FormData> rowList, Context context ) {
        this.rowList = rowList;
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_detail, parent, false);
        name = itemView.findViewById(R.id.name);
        mobileNumber = itemView.findViewById(R.id.mobileNumber);
        email = itemView.findViewById(R.id.email);
        bankName = itemView.findViewById(R.id.bankName);
        branchName = itemView.findViewById(R.id.branchName);
        accountNumber = itemView.findViewById(R.id.accountNumber);
        availablePoints = itemView.findViewById(R.id.availablePoints);
        firebaseDatabase= FirebaseDatabase.getInstance();
        databaseReferenceForPoints=firebaseDatabase.getReference("PointData");
        return new UsersLisViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        UsersLisViewHolder tmpHolder = (UsersLisViewHolder)holder;
        FormData rowData = rowList.get(position);
        name.setText("Name -"+rowData.getName());
        mobileNumber.setText("Mobile Number -"+rowData.getMobileNumber());
        email.setText("Email -"+rowData.getEmail());
        bankName.setText("Bank Name -"+rowData.getBankName());
        branchName.setText("Branch Name -"+rowData.getBranchName());
        accountNumber.setText("Account Number -"+rowData.getAccountNumber());
        availablePoints.setText("Points -"+rowData.getPoint());
//        databaseReferenceForPoints.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
//                {
//                    PointData pointData=dataSnapshot1.getValue(PointData.class);
//                    if((pointData.getEmail().equals(rowData.getEmail()))){
//                        availablePoints.setText("Points - "+String.valueOf(pointData.getPoint()));
//                    }else{
//                        availablePoints.setText("Points - 0");
//                    }
//                }
//                if(dataSnapshot.getValue() == null){
//                    availablePoints.setText("Points - 0");
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return rowList != null ? rowList.size() : 0;
    }
}

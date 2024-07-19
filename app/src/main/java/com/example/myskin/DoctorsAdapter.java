package com.example.myskin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class DoctorsAdapter extends RecyclerView.Adapter<DoctorsAdapter.DoctorViewHolder> {

    private List<Doctor> doctorList;

    public DoctorsAdapter(List<Doctor> doctorList) {
        this.doctorList = doctorList;
    }

    @NonNull
    @Override
    public DoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_doctor, parent, false);
        return new DoctorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorViewHolder holder, int position) {
        Doctor doctor = doctorList.get(position);
        holder.nameTextView.setText(doctor.getName());
        holder.lastNameTextView.setText(doctor.getLastName());
        holder.phoneTextView.setText(doctor.getPhone());
        holder.locationTextView.setText(doctor.getLocation());

        if (doctor.getProfilePictureUrl() != null) {
            Picasso.get().load(doctor.getProfilePictureUrl()).into(holder.profileImageView);
        } else {
            holder.profileImageView.setImageResource(R.drawable.info); // A default image in case there's no profile picture
        }
    }

    @Override
    public int getItemCount() {
        return doctorList.size();
    }

    static class DoctorViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView lastNameTextView;
        TextView phoneTextView;
        TextView locationTextView;
        ImageView profileImageView;

        public DoctorViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            lastNameTextView = itemView.findViewById(R.id.lastNameTextView);
            phoneTextView = itemView.findViewById(R.id.phoneTextView);
            locationTextView = itemView.findViewById(R.id.locationTextView);
            profileImageView = itemView.findViewById(R.id.profileImageView);
        }
    }
}

package lb.edu.ul.khayme_w_nar.ui.available_camps;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import lb.edu.ul.khayme_w_nar.R;
import lb.edu.ul.khayme_w_nar.RecyclerViewAdapter;
import lb.edu.ul.khayme_w_nar.databinding.FragmentAvailableCampsBinding;
import lb.edu.ul.khayme_w_nar.db.Activities;
import lb.edu.ul.khayme_w_nar.db.AppDatabase;
import lb.edu.ul.khayme_w_nar.db.CampPlace;
import lb.edu.ul.khayme_w_nar.db.DatabaseManager;
import lb.edu.ul.khayme_w_nar.ui.description.DescriptionFragment;

public class AvailableCampsFragment extends Fragment implements RecyclerViewAdapter.OnItemClickListener{
    private FragmentAvailableCampsBinding binding;


    public static AppDatabase db;
    List<CampPlace> allCamps;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAvailableCampsBinding.inflate(inflater, container, false);
        //get a global instance of the database
        db = DatabaseManager.getInstance(requireContext());
        new Thread(() ->{
            List<CampPlace> campingPlaces = db.CampDAO().getAllCamps();
            if(campingPlaces.isEmpty()){
                CampPlace C1 = new CampPlace();
                C1.setCampId(1);
                C1.setCampName("Glamping_Lebanon");
                C1.setCampDescription("Glamping Lebanon is a  luxury camping site located in Maghdouche,South lebanon, that enables you to bask in nature. It is also a place of rediscovery where you can soothe your spirit, meditation, and connect with the outdoors.");
                C1.setCampLocation("Maghdouche");
                C1.setCampPrice(20);
                C1.setCampImage("glamping_leb");

                db.CampDAO().insertCamp(C1);

                Activities A1 = new Activities();
                A1.setCamp_Id(1);
                A1.setActivityName("Hiking");
                A1.setActivityDesc("Explore the beautiful surroundings of the camp, including the olive groves and the hillsides. You can enjoy a peaceful walk or go for a hike to take in the panoramic views of the mountains and the Mediterranean Sea.");
                A1.setActIcon("hiking");
                db.actDao().insertAct(A1);

                Activities A2 = new Activities();
                A2.setCamp_Id(1);
                A2.setActivityName("Stargazing");
                A2.setActivityDesc("At night, the camp offers an excellent opportunity for stargazing, away from the city lights. You can relax around the fire pit and enjoy the tranquil atmosphere.");
                A2.setActIcon("telescope");
                db.actDao().insertAct(A2);

                Activities A3 = new Activities();
                A3.setCamp_Id(1);
                A3.setActivityName("Swimming");
                A3.setActivityDesc("Relax by the camp's serene waterfall or enjoy a soothing dip in a private Jacuzzi (available upon reservation for an extra fee).");
                A3.setActIcon("swimming");
                db.actDao().insertAct(A3);

                Activities A4 = new Activities();
                A4.setCamp_Id(1);
                A4.setActivityName("Barbecue");
                A4.setActivityDesc(" Enjoy the outdoor dining experience with meals prepared on-site at the restaurant or arrange a BBQ for a more casual gathering.");
                A4.setActIcon("barbecue");
                db.actDao().insertAct(A4);




                CampPlace C2 = new CampPlace();
                C2.setCampId(2);
                C2.setCampName("Koura Camp");
                C2.setCampDescription("Sleep in huts, and enjoy a swimming pool with a clear and unobstructed view of the Koura countryside.  It consists of 3 zones, each with a private pool. Zones can accommodate up to 13 people with sleeping arrangements, and the whole campsite can accommodate up to 70 people for an event. Recently they opened a tree house for 2 people and has a private pool.");
                C2.setCampLocation("Koura");
                C2.setCampPrice(25);
                C2.setCampImage("kouracamp");

                db.CampDAO().insertCamp(C2);

                Activities A5 = new Activities();
                A5.setCamp_Id(2);
                A5.setActivityName("Swimming");
                A5.setActivityDesc("Enjoy the swimming pool while taking in the scenic views of the Koura countryside.");
                A5.setActIcon("swimming");
                db.actDao().insertAct(A5);

                Activities A6 = new Activities();
                A6.setCamp_Id(2);
                A6.setActivityName("Hiking");
                A6.setActivityDesc("Explore the beautiful surroundings of the camp, including the olive groves and the hillsides. You can enjoy a peaceful walk or go for a hike to take in the panoramic views of the mountains and the Mediterranean Sea.");
                A6.setActIcon("hiking");
                db.actDao().insertAct(A6);

                Activities A7 = new Activities();
                A7.setCamp_Id(2);
                A7.setActivityName("Barbecue");
                A7.setActivityDesc("Arrange a barbecue session or cook in the camp’s kitchen facilities for a fun, casual meal with family and friends.");
                A7.setActIcon("barbecue");
                db.actDao().insertAct(A7);




                CampPlace C3 = new CampPlace();
                C3.setCampId(3);
                C3.setCampName("Uzit Cabin");
                C3.setCampDescription("UŽÍT Cabins, nestled in the serene Mazraat el Teffah forest near Ehden,Lebanon , offers a tranquil retreat for nature enthusiasts. The cabins are designed to provide a cozy and private experience, making them ideal for couples or families");
                C3.setCampLocation("Ehden");
                C3.setCampPrice(45);
                C3.setCampImage("uzitcabin");

                db.CampDAO().insertCamp(C3);

                Activities A8 = new Activities();
                A8.setCamp_Id(3);
                A8.setActivityName("Barbecue");
                A8.setActivityDesc("Arrange a barbecue session or cook in the camp’s kitchen facilities for a fun, casual meal with family and friends.");
                A8.setActIcon("barbecue");
                db.actDao().insertAct(A8);

                Activities A9 = new Activities();
                A9.setCamp_Id(3);
                A9.setActivityName("Hiking");
                A9.setActivityDesc("Explore the beautiful surroundings of the camp, including the olive groves and the hillsides. You can enjoy a peaceful walk or go for a hike to take in the panoramic views of the mountains and the Mediterranean Sea.");
                A9.setActIcon("hiking");
                db.actDao().insertAct(A9);

                Activities A10 = new Activities();
                A10.setCamp_Id(3);
                A10.setActivityName("Nature walks");
                A10.setActivityDesc("Take a leisurely stroll through the natural surroundings, breathing in the fresh air and enjoying the peace of the area.\n");
                A10.setActIcon("walk");
                db.actDao().insertAct(A10);




                CampPlace C4 = new CampPlace();
                C4.setCampId(4);
                C4.setCampName("The Pondales");
                C4.setCampDescription("Escape to nature at The Pondales Campsite! Unwind by the tranquil waters, enjoy breathtaking views, and create unforgettable memories. Perfect for adventurers and relaxation seekers");
                C4.setCampLocation("Bsharri");
                C4.setCampPrice(35);
                C4.setCampImage("thepondales");

                db.CampDAO().insertCamp(C4);

                Activities A11 = new Activities();
                A11.setCamp_Id(4);
                A11.setActivityName("Camping");
                A11.setActivityDesc("Set up a tent and enjoy a night under the stars in the peaceful surroundings of Bsharri.\n");
                A11.setActIcon("bonfire");
                db.actDao().insertAct(A11);

                Activities A24 = new Activities();
                A24.setCamp_Id(4);
                A24.setActivityName("Hiking");
                A24.setActivityDesc("Explore nearby trails to immerse yourself in nature");
                A24.setActIcon("hiking");
                db.actDao().insertAct(A24);

                Activities A25 = new Activities();
                A25.setCamp_Id(4);
                A25.setActivityName("Yoga And Meditation");
                A25.setActivityDesc("Utiliza the serene environmet for morning yoga sessions or meditation practices");
                A25.setActIcon("hiking");
                db.actDao().insertAct(A25);

                CampPlace C5 = new CampPlace();
                C5.setCampId(5);
                C5.setCampName("VonVoyage");
                C5.setCampDescription("It’s great for camping, spending the night in the van or even surprise your loved one with a lovely romantic night. The team is extremely nice and supportive which gives the place additional positive vibes.");
                C5.setCampLocation("Faitroun");
                C5.setCampPrice(20);
                C5.setCampImage("vonvoyage_camp");

                db.CampDAO().insertCamp(C5);

                Activities A12 = new Activities();
                A12.setCamp_Id(5);
                A12.setActivityName("Hiking");
                A12.setActivityDesc("The region around Von Voyage Camp offers various hiking trails with breathtaking views. It's a great way to explore the surrounding forests and mountains");
                A12.setActIcon("hiking");
                db.actDao().insertAct(A12);

                Activities A13 = new Activities();
                A13.setCamp_Id(5);
                A13.setActivityName("Cycling");
                A13.setActivityDesc("Rent a bike or bring your own to explore the picturesque landscapes around the camp. Cycling is a great way to experience the natural beauty of Faytroun ");
                A13.setActIcon("cycling");
                db.actDao().insertAct(A13);



                CampPlace C6 = new CampPlace();
                C6.setCampId(6);
                C6.setCampName("Swings Camp");
                C6.setCampDescription("Swings is an outdoor activity park and campground located in a beautiful pine forest not far away from the city Beirut. This place is popular for picnics, camping and high adrenaline activities for all ages. Most popular activities are: Giant Swing, Aerial adventure courses.");
                C6.setCampLocation("Zaraaoun");
                C6.setCampPrice(20);
                C6.setCampImage("swings");

                db.CampDAO().insertCamp(C6);

                Activities A14 = new Activities();
                A14.setCamp_Id(6);
                A14.setActivityName("Outdoor Cooking");
                A14.setActivityDesc("You can set up a barbecue or use campfire facilities to cook outdoors, perfect for a picnic or a relaxed meal in nature ");
                A14.setActIcon("barbecue");
                db.actDao().insertAct(A14);

                Activities A15 = new Activities();
                A15.setCamp_Id(6);
                A15.setActivityName("Swimmimg and Ziplining");
                A15.setActivityDesc(" As the name suggests, Swings Camp offers swings and potentially ziplining for thrilling experiences amidst the trees. This can be a great adventure for both kids and adults.");
                A15.setActIcon("swimming");
                db.actDao().insertAct(A15);



                CampPlace C7 = new CampPlace();
                C7.setCampId(7);
                C7.setCampName("Cedars Ground Composite");
                C7.setCampDescription("A citys' escape to a communal campsite that is home to Lebanon's legendary cedar trees and a growing, outdoorsy community. We're always open and excited to meet you, so call to reserve in advance.");
                C7.setCampLocation("Barouk");
                C7.setCampPrice(20);
                C7.setCampImage("cedars_ground_composite");

                db.CampDAO().insertCamp(C7);

                Activities A16 = new Activities();
                A16.setCamp_Id(7);
                A16.setActivityName("HorseBack Riding");
                A16.setActivityDesc("Experience the landscape on horseback");
                A16.setActIcon("horse_riding");
                db.actDao().insertAct(A16);

                Activities A17 = new Activities();
                A17.setCamp_Id(7);
                A17.setActivityName("Wild Herbs Foraging");
                A17.setActivityDesc("Learn to identify and collect wild herbs.");
                A17.setActIcon("man");
                db.actDao().insertAct(A17);

                Activities A18 = new Activities();
                A18.setCamp_Id(7);
                A18.setActivityName("Guided Hikes");
                A18.setActivityDesc("Explore the Shouf Reserve with knowledgeable guides.");
                A18.setActIcon("hiking");
                db.actDao().insertAct(A18);

                Activities A19 = new Activities();
                A19.setCamp_Id(7);
                A19.setActivityName("Apple Picking");
                A19.setActivityDesc(" Seasonally, engage in apple harvesting.\n");
                A19.setActIcon("apple");
                db.actDao().insertAct(A19);

                Activities A20 = new Activities();
                A20.setCamp_Id(7);
                A20.setActivityName("Skiing");
                A20.setActivityDesc("During winter, people enjoy skiing and other snow-related activities with a ski shuttle service available.");
                A20.setActIcon("ski_resort");
                db.actDao().insertAct(A20);



                CampPlace C8 = new CampPlace();
                C8.setCampId(8);
                C8.setCampName("Wood Hills Hotel & Resort");
                C8.setCampDescription("Wood Hills Resort is the ideal retreat to enjoy your stay. With 24 enhanced luxurious bungalows offering outdoor seating with designated views and a multitude of to do activities and beautiful outdoors you can relax, get active, and enjoy the ultimate combination of nature and luxurious experience");
                C8.setCampLocation("Mairouba");
                C8.setCampPrice(50);
                C8.setCampImage("woodhills");

                db.CampDAO().insertCamp(C8);

                Activities A21 = new Activities();
                A21.setCamp_Id(8);
                A21.setActivityName("Paragliding");
                A21.setActivityDesc("Experience the thrill of paragliding with the first paragliding club in Lebanon, established in 1992.");
                A21.setActIcon("parachute");
                db.actDao().insertAct(A21);

                Activities A22 = new Activities();
                A22.setCamp_Id(8);
                A22.setActivityName("Skiing");
                A22.setActivityDesc("During winter, people enjoy skiing and other snow-related activities with a ski shuttle service available.");
                A22.setActIcon("skiing");
                db.actDao().insertAct(A22);

                Activities A23 = new Activities();
                A23.setCamp_Id(8);
                A23.setActivityName("Hiking");
                A23.setActivityDesc("Nature enthusiasts can explore an easy-level hiking trail carved out within the resort's countryside, allowing guests to immerse themselves in the local wildlife and scenic beauty");
                A23.setActIcon("hiking");
                db.actDao().insertAct(A23);
            }
        }).start();
        return binding.getRoot();
    }

@Override
public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    new Thread(() -> {
        allCamps = db.CampDAO().getAllCamps();
        requireActivity().runOnUiThread(() -> {
            RecyclerViewAdapter adapter = new RecyclerViewAdapter(requireContext(), allCamps, AvailableCampsFragment.this);
            binding.availableCampsRv.setLayoutManager(new LinearLayoutManager(getContext()));
            binding.availableCampsRv.setAdapter(adapter);
        });
    }).start();
}

    @Override
    public void onItemClick(CampPlace camp) {
        Bundle loginBundle = getArguments();
        boolean isLoggedIn = loginBundle != null
                ? loginBundle.getBoolean("isLoggedIn", false) // Consistent key
                : false;
        Log.d("AvailableCampsFragment", "isLoggedIn from Bundle: " + isLoggedIn);

        Bundle B = new Bundle();
        B.putInt("campId", camp.getCampId());
        B.putBoolean("isLoggedIn", isLoggedIn); // Pass the status to the next fragment

        NavController navController = Navigation.findNavController(requireView());
        navController.navigate(R.id.action_to_description, B);
    }

    @Override
    public void onButtonClick(int campId) {
        Bundle B = new Bundle();
        B.putInt("campId", campId);

        NavController navController = Navigation.findNavController(requireView());
        navController.navigate(R.id.action_to_activities, B);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
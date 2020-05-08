package com.example.mye_commerceapplication;
import android.content.Intent;
import android.os.Bundle;
import com.example.mye_commerceapplication.Model.Product;
import com.example.mye_commerceapplication.Prevalent.Prevalent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.navigation.ui.AppBarConfiguration;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private DatabaseReference mProductsRef,mSalesRef;
    private RecyclerView listView;
    private ArrayList<Product> list_products;
    private EditText searchView;
    private Button homeSearchbtn;
    private String word;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Home");

        searchView=this.findViewById(R.id.home_search_text);
        word=searchView.getText().toString();
        //Prevalent.searchedword=word;

        Prevalent.numberOfCommandText=findViewById(R.id.number_of_commands_number);

        homeSearchbtn=this.findViewById(R.id.home_search_btn);
        homeSearchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!TextUtils.isEmpty(word)) {

                    Toast.makeText(HomeActivity.this, "searched word is : " + word, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(HomeActivity.this, CartActivity.class);

                    intent.putExtra("product_description",word);
                    startActivity(intent);
                }
                else{
                    //Toast.makeText(HomeActivity.this, "please enter a valid searched product !", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mProductsRef= FirebaseDatabase.getInstance().getReference("Products");
        Paper.init(this);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //redirection vers la page de commandes
                Intent intent=new Intent(HomeActivity.this,CartActivity.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);

        View headerView=navigationView.getHeaderView(0);
        TextView usernameTextView=headerView.findViewById(R.id.user_profile_name);
        ImageView imageViewName=headerView.findViewById(R.id.user_profile_image);

        usernameTextView.setText(Prevalent.currentOnlineUser.getName());

        //set the Number of Commands of Client...
        mSalesRef=FirebaseDatabase.getInstance().getReference("ligneCommande").child(Prevalent.currentOnlineUser.getPhone());
        mSalesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data:dataSnapshot.getChildren()){
                    Prevalent.numberOfCommands++;
                }
                Prevalent.numberOfCommandText.setText(String.valueOf(Prevalent.numberOfCommands));

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        return false;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_cart)
        {

        }

        else if (id == R.id.nav_orders)
        {
            Intent intent=new Intent(HomeActivity.this,SalesActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_categories)
        {

        }
        else if (id == R.id.nav_settings)
        {
            Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
            startActivity(intent);
        }

        else if(id==R.id.nav_boutique){

        }
        else if (id == R.id.nav_logout)
        {
            Paper.book().destroy();

            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        list_products=new ArrayList<Product>();

        mProductsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot n:dataSnapshot.getChildren()){
                    Product note=n.getValue(Product.class);
                    list_products.add(note);
                }

                listView =findViewById(R.id.recycler_menu);
                listView.setLayoutManager(new LinearLayoutManager(HomeActivity.this, RecyclerView.VERTICAL,false));

                ProductsAdapter productsAdapter=new ProductsAdapter(HomeActivity.this,list_products);//,HomeActivity.this);
                listView.setAdapter(productsAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}

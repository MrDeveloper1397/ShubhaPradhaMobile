package task.marami.Shubhaprada;

import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import task.marami.Shubhaprada.Fragments.CommissionApproval;
import task.marami.Shubhaprada.Fragments.CostApproval;
import task.marami.Shubhaprada.Fragments.DiscountApproval;
import task.marami.Shubhaprada.Fragments.PlotApproval;
import task.marami.Shubhaprada.Utils.Contents;

public class ApprovalScreen extends AppCompatActivity {
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    int plistpos,pblistpos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approval_screen);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        plistpos = getIntent().getIntExtra("plistpos", 0);
        pblistpos = getIntent().getIntExtra("passbookpos", 0);
        Contents.utiPassbookList.get(pblistpos).setPlot_appr(checkStatus(Contents.utiPassbookList.get(pblistpos).getPlot_appr()));
        Contents.utiPassbookList.get(pblistpos).setCost_appr(checkStatus(Contents.utiPassbookList.get(pblistpos).getCost_appr()));
        Contents.utiPassbookList.get(pblistpos).setDiscou_appr(checkStatus(Contents.utiPassbookList.get(pblistpos).getDiscou_appr()));
        Contents.utiPassbookList.get(pblistpos).setComm_appr(checkStatus(Contents.utiPassbookList.get(pblistpos).getComm_appr()));

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

    }
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle=new Bundle();
            bundle.putInt("plistpos", plistpos);
            bundle.putInt("passbookpos", pblistpos);
            switch (position)
            {
                case 0:
                    PlotApproval tab1=new PlotApproval();
                    tab1.setArguments(bundle);
                    return tab1;
                case 1:
                    CostApproval tab2 = new CostApproval();
                    tab2.setArguments(bundle);
                    return tab2;
                case 2: DiscountApproval tab3=new DiscountApproval();
                    tab3.setArguments(bundle);
                    return  tab3;

                case 3: CommissionApproval tab4=new CommissionApproval();
                    tab4.setArguments(bundle);
                    return  tab4;
            }
            return null;
        }
        @Override
        public int getCount() {
            return 4;
        }
    }

    public String checkStatus(String str)
    {
        if(str.equals("Y"))
        {
            return "Y";
        }else
        {
            return "N";
        }
    }
}

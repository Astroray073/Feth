package com.kyigames.feth;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.database.annotations.NotNull;
import com.kyigames.feth.model.Character;
import com.kyigames.feth.model.ClassCategory;
import com.kyigames.feth.model.Database;
import com.kyigames.feth.model.Loss;
import com.kyigames.feth.model.Present;
import com.kyigames.feth.model.UnitClass;
import com.kyigames.feth.view.CharacterContent;
import com.kyigames.feth.view.CharacterHeader;
import com.kyigames.feth.view.ClassCategoryHeader;
import com.kyigames.feth.view.ClassContent;
import com.kyigames.feth.view.ClassHeader;
import com.kyigames.feth.view.FactionHeader;
import com.kyigames.feth.view.LicenseItem;
import com.kyigames.feth.view.LossContent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import devlight.io.library.ntb.NavigationTabBar;
import eu.davidea.fastscroller.FastScroller;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.IFlexible;

class PageManager extends PagerAdapter {
    private Context m_context;
    private LayoutInflater m_inflater;

    // Extra
    private FastScroller m_fastScroller;

    public PageManager(@NotNull Context context) {
        super();
        m_context = context;
        m_inflater = LayoutInflater.from(context);
    }

    private Resources getResources() {
        return m_context.getResources();
    }

    public List<NavigationTabBar.Model> buildTabMenus() {
        final String[] titles = getResources().getStringArray(R.array.menu_title);
        final String[] colors = getResources().getStringArray(R.array.menu_color);
        final TypedArray icons = getResources().obtainTypedArray(R.array.menu_icon);

        ArrayList<NavigationTabBar.Model> models = new ArrayList<>();

        for (int i = 0; i < titles.length; ++i) {
            Drawable iconRes = m_context.getDrawable(icons.getResourceId(i, 0));
            int colorRes = Color.parseColor(colors[i]);
            String title = titles[i];

            models.add(
                    new NavigationTabBar.Model.Builder(
                            iconRes,
                            colorRes)
                            .selectedIcon(iconRes)
                            .title(title)
                            .build()
            );
        }

        icons.recycle();
        return models;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public boolean isViewFromObject(final View view, final Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(final View container, final int position, final Object object) {
        ((ViewPager) container).removeView((View) object);
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        View view = null;
        Log.d("Main", "Init view " + position);

        if (position == 0) // Character
        {
            view = m_inflater.inflate(R.layout.page_character, null, false);
            initializeCharacterPage(view);
        } else if (position == 1) // Class
        {
            view = m_inflater.inflate(R.layout.page_class, null, false);
            initializeClassPage(view);
        } else if (position == 2) // Loss
        {
            view = m_inflater.inflate(R.layout.page_loss, null, false);
            initializeLossPage(view);
        } else if (position == 3) // License
        {
            view = m_inflater.inflate(R.layout.page_license, null, false);
            initializeLicensePage(view);
        } else {
            view = m_inflater.inflate(R.layout.page_wip, null, false);
        }
        container.addView(view);
        return view;
    }

    private void initializeCharacterPage(@NotNull View view) {
        List<Character> characterList = Database.getTable(Character.class);
        List<Present> presentList = Database.getTable(Present.class);

        FactionHeader none = new FactionHeader(m_context.getString(R.string.faction_none), R.color.colorFactionNone, R.drawable.ic_character);
        FactionHeader eagle = new FactionHeader(m_context.getString(R.string.faction_eagle), R.color.colorFactionEagle, R.drawable.ic_flag_eagle);
        FactionHeader lion = new FactionHeader(m_context.getString(R.string.faction_lion), R.color.colorFactionLion, R.drawable.ic_flag_lion);
        FactionHeader deer = new FactionHeader(m_context.getString(R.string.faction_deer), R.color.colorFactionDeer, R.drawable.ic_flag_deer);
        FactionHeader church = new FactionHeader(m_context.getString(R.string.faction_church), R.color.colorFactionChurch, R.drawable.ic_flag_church);

        addFactionMembers(none, new String[]{getResources().getString(R.string.hero_name)});
        addFactionMembers(eagle, getResources().getStringArray(R.array.eagle_members));
        addFactionMembers(lion, getResources().getStringArray(R.array.lion_members));
        addFactionMembers(deer, getResources().getStringArray(R.array.deer_members));
        addFactionMembers(church, getResources().getStringArray(R.array.church_members));

        List<IFlexible> factionHeaders = new ArrayList<>();
        factionHeaders.add(none);
        factionHeaders.add(eagle);
        factionHeaders.add(lion);
        factionHeaders.add(deer);
        factionHeaders.add(church);

        FlexibleAdapter<IFlexible> adapter = new FlexibleAdapter<>(factionHeaders);
        adapter.setDisplayHeadersAtStartUp(true);
        adapter.setStickyHeaders(true);
        adapter.expandItemsAtStartUp();

        RecyclerView pg_character = view.findViewById(R.id.character_list_view);
        pg_character.setAdapter(adapter);
    }

    private void addFactionMembers(@NotNull FactionHeader factionHeader, @NotNull String[] memberNames) {
        for (String memberName : memberNames) {
            Character character = Database.getEntityByKey(Character.class, memberName);

            CharacterContent characterContent = new CharacterContent(character);
            CharacterHeader characterHeader = new CharacterHeader(factionHeader, character);

            characterHeader.addSubItem(characterContent);
            factionHeader.addSubItem(characterHeader);
        }
    }

    private void initializeClassPage(@NotNull View view) {
        final String[] categoryColors = m_context.getResources().getStringArray(R.array.class_category_colors);

        List<ClassCategory> classCategoryList = Database.getTable(ClassCategory.class);
        List<UnitClass> unitClassList = Database.getTable(UnitClass.class);

        Collections.sort(classCategoryList, new Comparator<ClassCategory>() {
            @Override
            public int compare(ClassCategory classCategory, ClassCategory t1) {
                return classCategory.MinLevel - t1.MinLevel;
            }
        });

        List<IFlexible> categoryHeaders = new ArrayList<>();
        for (int i = 0; i < classCategoryList.size(); i++) {
            ClassCategory classCategory = classCategoryList.get(i);
            ClassCategoryHeader categoryHeader = new ClassCategoryHeader(classCategory,
                    categoryColors[i]);

            for (UnitClass unitClass : unitClassList) {
                if (unitClass.Category.equals(classCategory.Name)) {
                    ClassHeader classHeader = new ClassHeader(categoryHeader, unitClass);
                    categoryHeader.addSubItem(classHeader);

                    ClassContent classContent = new ClassContent(unitClass);
                    classHeader.addSubItem(classContent);
                }
            }

            categoryHeaders.add(categoryHeader);
        }

        FlexibleAdapter<IFlexible> adapter = new FlexibleAdapter<>(categoryHeaders);
        adapter.setDisplayHeadersAtStartUp(true);
        adapter.setStickyHeaders(true);
        adapter.expandItemsAtStartUp();

        RecyclerView page_class = view.findViewById(R.id.class_list_view);
        page_class.setAdapter(adapter);
    }

    private void initializeLossPage(@NotNull View view) {
        // Get table from db.
        List<Loss> table = Database.getTable(Loss.class);
        Collections.sort(table);

        List<IFlexible> model = new ArrayList<>();
        for (Loss loss : table) {
            model.add(new LossContent(loss));
        }

        // Initialize the Adapter
        FlexibleAdapter<IFlexible> adapter = new LossContent.Adapter(model, null, true);

        // Initialize the RecyclerView and attach the Adapter to it as usual
        RecyclerView recyclerView = view.findViewById(R.id.loss_list_view);
        recyclerView.setAdapter(adapter);

        // Then, add FastScroller to the RecyclerView
        m_fastScroller = view.findViewById(R.id.fast_scroller);
        m_fastScroller.setAutoHideEnabled(false);             //true is the default value
        //m_fastScroller.setAutoHideDelayInMillis(1000L);      //1000ms is the default value
        m_fastScroller.setHandleAlwaysVisible(true);        //false is the default value
        m_fastScroller.setIgnoreTouchesOutsideHandle(false); //false is the default value
        // 0 pixel is the default value. When > 0 it mimics the fling gesture
        m_fastScroller.setMinimumScrollThreshold(0);
        // OnScrollStateChangeListener remains optional
        //m_fastScroller.addOnScrollStateChangeListener(this);
        //m_fastScroller.removeOnScrollStateChangeListener(this);
        // The color (accentColor) is automatically fetched by the FastScroller constructor,
        // but you can change it at runtime:
        m_fastScroller.setBubbleAndHandleColor(Color.RED);

        // Finally, assign the Fastscroller to the Adapter
        adapter.setFastScroller(m_fastScroller);
    }

    private void initializeLicensePage(@NotNull View view) {
        String[] licenses = getResources().getStringArray(R.array.licenses);
        String[] license_types = getResources().getStringArray(R.array.license_types);
        String[] license_url = getResources().getStringArray(R.array.license_agreement_url);

        List<IFlexible> licenseItemList = new ArrayList<>();

        for (int i = 0; i < licenses.length; ++i) {
            licenseItemList.add(new LicenseItem(licenses[i], license_types[i], license_url[i]));
        }

        FlexibleAdapter<IFlexible> adapter = new FlexibleAdapter<>(licenseItemList);

        RecyclerView recyclerView = view.findViewById(R.id.license_list_view);
        recyclerView.setAdapter(adapter);
    }
}

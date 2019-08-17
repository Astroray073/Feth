package com.kyigames.feth.model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kyigames.feth.OnProgressChangeListener;
import com.kyigames.feth.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class Database {
    private static final String TAG = Database.class.getSimpleName();

    private static FirebaseDatabase m_database = FirebaseDatabase.getInstance();
    private static Map<String, Object> m_tables = new HashMap<>();
    private static Map<String, Class> m_tableTypes = new HashMap<>();

    // Image resources
    private static Map<String, Integer> m_portrait = new HashMap<>();

    private static <T> void registerTable(Class<T> tableType) {
        m_tables.put(tableType.getSimpleName(), new ArrayList<T>());
        m_tableTypes.put(tableType.getSimpleName(), tableType);
    }

    static {
        // Register table types
        registerTable(Ability.class);
        registerTable(Character.class);
        registerTable(ClassCategory.class);
        registerTable(CombatArts.class);
        registerTable(Crest.class);
        registerTable(Gift.class);
        registerTable(Loss.class);
        registerTable(Present.class);
        registerTable(Spell.class);
        registerTable(UnitClass.class);
        registerTable(Tea.class);

        // Portrait mapping
        m_portrait.put("주인공", R.drawable.byleth);

        m_portrait.put("에델가르드", R.drawable.edelgard);
        m_portrait.put("휴베르트", R.drawable.hubert);
        m_portrait.put("도로테아", R.drawable.dorothea);
        m_portrait.put("페르디난트", R.drawable.ferdinand);
        m_portrait.put("베르나데타", R.drawable.bernadetta);
        m_portrait.put("카스파르", R.drawable.caspar);
        m_portrait.put("페트라", R.drawable.petra);
        m_portrait.put("린하르트", R.drawable.linhardt);

        m_portrait.put("디미트리", R.drawable.dimitri);
        m_portrait.put("두두", R.drawable.doudou);
        m_portrait.put("펠릭스", R.drawable.felix);
        m_portrait.put("메르세데스", R.drawable.mercedes);
        m_portrait.put("애쉬", R.drawable.ashe);
        m_portrait.put("아네트", R.drawable.anette);
        m_portrait.put("실뱅", R.drawable.sylvain);
        m_portrait.put("잉그리트", R.drawable.ingrid);

        m_portrait.put("클로드", R.drawable.claude);
        m_portrait.put("로렌츠", R.drawable.lorentz);
        m_portrait.put("힐다", R.drawable.hilda);
        m_portrait.put("라파엘", R.drawable.raphael);
        m_portrait.put("리시테아", R.drawable.lysithea);
        m_portrait.put("이그나츠", R.drawable.ignace);
        m_portrait.put("마리안", R.drawable.marianne);
        m_portrait.put("레오니", R.drawable.leonie);
        m_portrait.put("레아", R.drawable.rhea);
        m_portrait.put("마누엘라", R.drawable.manuela);
        m_portrait.put("한네만", R.drawable.hanneman);
        m_portrait.put("세테스", R.drawable.seteth);
        m_portrait.put("흐렌", R.drawable.flayn);
        m_portrait.put("시릴", R.drawable.cyril);
        m_portrait.put("제랄트", R.drawable.jeralt);
        m_portrait.put("카트린", R.drawable.catherine);
        m_portrait.put("알로이스", R.drawable.alois);
        m_portrait.put("길베르트", R.drawable.gilbert);
        m_portrait.put("샤미아", R.drawable.shamir);
    }

    public static int tableCount() {
        return m_tables.size();
    }

    public static void loadAll(@Nullable final OnProgressChangeListener progressChangeListener) {
        m_database.getReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int currentProgress = 0;

                for (DataSnapshot table : dataSnapshot.getChildren()) {
                    String tableName = table.getKey();

                    retrieveData(tableName, table);

                    ++currentProgress;
                    if (progressChangeListener != null) {
                        progressChangeListener.onProgressChanged(currentProgress);
                    }
                }

                if (progressChangeListener != null) {
                    progressChangeListener.onComplete();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "Loading canceled.");
            }
        });
    }

    public static <T> List<T> getTable(String tableName) {
        return (List<T>) m_tables.get(tableName);
    }

    public static <T> List<T> getTable(Class<T> tableType) {
        return getTable(tableType.getSimpleName());
    }

    public static Class getTableType(String tableName) {
        return m_tableTypes.get(tableName);
    }

    private static <T> void retrieveData(String tableName, DataSnapshot dataSnapshot) {
        Log.d(TAG, "retrieveData " + tableName);

        List<T> container = getTable(tableName);
        Class<T> tableType = getTableType(tableName);

        for (DataSnapshot row : dataSnapshot.getChildren()) {
            container.add(row.getValue(tableType));
        }
    }

    public static int getPortrait(String characterName) {
        return m_portrait.get(characterName);
    }

    public static <T extends IDbEntity> T getEntityByKey(Class<T> type, final String key) {
        return getTable(type).stream()
                .filter(new Predicate<T>() {
                    @Override
                    public boolean test(T t) {
                        return t.getKey().equals(key);
                    }
                })
                .findAny()
                .orElse(null);
    }
}

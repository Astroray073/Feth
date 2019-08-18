package com.kyigames.feth.model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kyigames.feth.OnProgressChangeListener;

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
        registerTable(Tea.class);
        registerTable(UnitClass.class);
    }

    public static int tableCount() {
        return m_tables.size();
    }

    public static void loadAll(@Nullable final OnProgressChangeListener progressChangeListener) {
        m_database.getReference().addListenerForSingleValueEvent(new ValueEventListener() {
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
            try {
                container.add(row.getValue(tableType));
            } catch (Exception e) {
                Log.d(TAG, "Failed to get data of type : " + tableType.getSimpleName());
            }
        }
    }

    public static int getPortrait(String characterName) {
        return m_portrait.get(characterName);
    }

    public static <T extends IDbEntity> T findEntityByKey(Class<T> type, final String key) {
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

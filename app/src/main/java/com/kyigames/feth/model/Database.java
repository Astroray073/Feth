package com.kyigames.feth.model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kyigames.feth.OnProgressChangeListener;
import com.kyigames.feth.utils.ResourceUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database {
    private static final String TAG = Database.class.getSimpleName();

    private static FirebaseDatabase m_database = FirebaseDatabase.getInstance();
    private static Map<String, Object> m_tables = new HashMap<>();
    private static Map<String, Class> m_tableTypes = new HashMap<>();

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
        loadAll_redirection(progressChangeListener);
    }

    private static void loadAll_redirection(@Nullable final OnProgressChangeListener progressChangeListener) {
        int versionCode = ResourceUtils.getVersionCode();

        if (versionCode < 5) {
            loadAll_3(progressChangeListener);
        } else {
            loadAll_5(progressChangeListener);
        }
    }

    private static void loadAll_3(@Nullable final OnProgressChangeListener progressChangeListener) {
        m_database.getReference()
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        int currentProgress = 0;

                        for (DataSnapshot table : dataSnapshot.getChildren()) {
                            String tableName = table.getKey();

                            if (!hasTable(tableName)) {
                                continue;
                            }

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

    // TODO: At version code 5, create firebase root table starting with version code.
    private static void loadAll_5(@Nullable final OnProgressChangeListener progressChangeListener) {

        m_database
                .getReference(Integer.toString(ResourceUtils.getVersionCode()))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        int currentProgress = 0;

                        for (DataSnapshot table : dataSnapshot.getChildren()) {
                            String tableName = table.getKey();

                            if (!hasTable(tableName)) {
                                continue;
                            }

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

    private static boolean hasTable(String tableName) {
        return m_tables.containsKey(tableName);
    }

    private static <T> List<T> getTable(String tableName) {
        return (List<T>) m_tables.get(tableName);
    }

    public static <T> List<T> getTable(Class<T> tableType) {
        return getTable(tableType.getSimpleName());
    }

    private static Class getTableType(String tableName) {
        return m_tableTypes.get(tableName);
    }

    private static <T> void retrieveData(String tableName, DataSnapshot dataSnapshot) {
        Log.d(TAG, "retrieveData " + tableName);

        Class<T> tableType = getTableType(tableName);
        List<T> container = getTable(tableName);

        for (DataSnapshot row : dataSnapshot.getChildren()) {
            try {
                container.add(row.getValue(tableType));
            } catch (Exception e) {
                Log.d(TAG, "Failed to get data of type : " + tableType.getSimpleName());
            }
        }
    }

    public static <T extends IDbEntity> T findEntityByKey(Class<T> type, final String key) {
        return getTable(type).stream()
                .filter(entity -> entity.getKey().equals(key))
                .findAny()
                .orElse(null);
    }
}

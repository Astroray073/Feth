package com.kyigames.feth.model;

import android.content.Context;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kyigames.feth.OnProgressChangeListener;
import com.kyigames.feth.R;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;

public class Database
{
    private static final String TAG = Database.class.getSimpleName();
    private static final String CHAR_SET = "UTF-8";

    static class DbTable
    {
        String Name;
        Class RowType;
        Object Rows;
    }

    private static Map<String, DbTable> m_tables = new HashMap<>();
    private static Gson m_gson = new Gson();

    static
    {
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
        registerTable(TeaParty.class);
        registerTable(UnitClass.class);
        registerTable(SideStory.class);
    }

    private static <T> void registerTable(Class<T> rowType)
    {
        DbTable table = new DbTable();
        table.Name = rowType.getSimpleName();
        table.RowType = rowType;
        m_tables.put(table.Name, table);
    }

    private static String readJsonString(Context context) throws IOException
    {
        try (InputStream inputStream = context.getResources().openRawResource(R.raw.database))
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, CHAR_SET));
            StringJoiner joiner = new StringJoiner("\n");
            String line;
            while ((line = reader.readLine()) != null)
            {
                joiner.add(line);
            }

            return joiner.toString();
        }
    }

    public static void loadAll(Context context, @Nullable final OnProgressChangeListener progressChangeListener) throws IOException, JSONException
    {
        int currentProgress = 0;

        String jsonString = readJsonString(context);
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(jsonString).getAsJsonObject();

        Set<Map.Entry<String, JsonElement>> entrySet = jsonObject.entrySet();
        for (Map.Entry<String, JsonElement> entry : entrySet)
        {
            if (!tableExists(entry.getKey()))
            {
                continue;
            }

            DbTable table = getTable(entry.getKey());
            Class<?> rowType = table.RowType;
            table.Rows = retrieveData(entry.getValue().getAsJsonArray(), rowType);

            ++currentProgress;
            if (progressChangeListener != null)
            {
                progressChangeListener.onProgressChanged(currentProgress);
            }
        }

        if (progressChangeListener != null)
        {
            progressChangeListener.onComplete();
        }
    }

    private static <T> List<T> retrieveData(JsonArray jsonTable, Class<T> rowType)
    {
        List<T> rows = new ArrayList<>();

        for (int i = 0; i < jsonTable.size(); ++i)
        {
            JsonElement jsonRow = jsonTable.get(i);
            T row = m_gson.fromJson(jsonRow, rowType);
            rows.add(row);
        }

        return rows;
    }

    public static <T> List<T> getTable(Class<T> rowType)
    {
        DbTable table = getTable(rowType.getSimpleName());

        if (table == null)
        {
            throw new NullPointerException(rowType.getSimpleName());
        }
        return (List<T>) table.Rows;
    }

    public static DbTable getTable(String tableName)
    {
        return m_tables.get(tableName);
    }

    public static <T> List<T> getTableRow(Class<T> rowType)
    {
        if (!m_tables.containsKey(rowType.getSimpleName()))
        {
            return null;
        }

        return (List<T>) m_tables.get(rowType.getSimpleName()).Rows;
    }

    public static boolean tableExists(String tableName)
    {
        return m_tables.containsKey(tableName);
    }

    public static <T extends IDbEntity> T findEntityByKey(Class<T> rowType, final String key)
    {
        if (!tableExists(rowType.getSimpleName()))
        {
            return null;
        }

        return Objects.requireNonNull(getTableRow(rowType))
                .stream()
                .filter(entity -> entity.getKey().equals(key))
                .findAny()
                .orElse(null);
    }


}

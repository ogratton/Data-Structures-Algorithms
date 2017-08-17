package tables.java;

import java.util.*;

public interface Table <Key extends Comparable<Key>,Value> {

  boolean containsKey(Key v);     // Self-explanatory.
  Optional<Value> get(Key k);     // Returns the value of a key, if it exists.
  boolean isEmpty();              // Self-explanatory.
  Table<Key,Value> put(Key k, Value v);     // Table with added or replaced entry.
  Optional<Table<Key,Value>> remove(Key k); // Removes the entry with given key, if present.
  int size();                     // Number of entries in the table.
  Collection<Value> values();     // The collection of values in the table.
  Collection<Key> keys();         // The set of keys in the table.
}
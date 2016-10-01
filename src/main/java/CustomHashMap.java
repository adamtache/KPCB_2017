package main.java;

import java.util.HashSet;
import java.util.Set;

/*
 * Custom Hash Map implementation
 * KPCB 2017 engineering fellows submission
 * author @adamtache
 */

public class CustomHashMap<K, V> {

	static class Entry<K, V>{
		K key;
		V value;
		Entry<K, V> next;

		public Entry(K key, V value){
			this.key = key;
			this.value = value;
			this.next = null;
		}

		@Override
		public boolean equals(Object o){
			if(o == this){
				return true;
			}
			if(o == null || !(o instanceof Entry)){
				return false;
			}
			Entry<K, V> other = (Entry<K, V>) o;
			Entry<K, V> thisEntry = this;
			while(other != null){
				if(thisEntry == null){
					return false;
				}
				if(other.key == null && thisEntry.key != null){
					return false;
				}
				if(other.key != null && thisEntry.key == null){
					return false;
				}
				if(other.key != null && thisEntry.key != null && !other.key.equals(thisEntry.key)){
					return false;
				}
				V v1 = other.value;
				V v2 = thisEntry.value;
				if(v1 == null && v2 != null){
					return false;
				}
				if(v1 != null && v2 == null){
					return false;
				}
				if(v1 != null && v2 != null && !v1.equals(v2)){
					return false;
				}
				other = other.next;
				thisEntry = thisEntry.next;
			}
			return true;
		}

		public String toString(){
			String str = "";
			Entry<K, V> head = this;
			while(head != null){
				str += head.key + ":" + head.value;
				head = head.next;
				str += head == null ? "" : ", ";
			}
			return str;
		}
		
		public Set<K> keySet(){
			Set<K> keys = new HashSet<>();
			Entry<K, V> head = this;
			while(head != null){
				keys.add(head.key);
				head = head.next;
			}
			return keys;
		}
		
		public Set<V> valueSet(){
			Set<V> values = new HashSet<>();
			Entry<K, V> head = this;
			while(head != null){
				values.add(head.value);
				head = head.next;
			}
			return values;
		}

	}

	private Entry<K, V>[] entries;
	private int items;

	public CustomHashMap(int size){
		this.entries = new Entry[size];
	}

	public boolean set(K key, V value){
		int hash = hash(key);
		if(hash >= entries.length){
			return false;
		}
		if(entries[hash] != null){
			Entry<K, V> entry = entries[hash];
			while(entry != null){
				if(entry.key == null && key == null){
					entry.value = value;
					return true;
				}
				if(entry.key.equals(key)){
					entry.value = value;
					return true;
				}
				Entry<K, V> next = entry.next;
				if(next == null){
					entry.next = new Entry(key, value);
					items++;
					return true;
				}
				entry = entry.next;
			}

		}
		entries[hash] = new Entry(key, value);
		items++;
		return true;
	}

	public Object get(K key){
		Entry<K, V> entry = entries[hash(key)];
		while(entry != null){
			if(entry.key == null && key == null){
				return entry.value;
			}
			if(entry.key != null && entry.key.equals(key)){
				return entry.value;
			}
		}
		return null;
	}

	public V delete(K key){
		int index = hash(key);
		Entry<K, V> entry = entries[index];
		Entry<K, V> prev = null;
		while(entry != null){
			V value = entry.value;
			Entry<K, V> next = entry.next;
			if(key == null && entry.key != null){
				prev = entry;
				entry = next;
			}
			if(key != null && entry.key == null){
				prev = entry;
				entry = next;
			}
			if((key == null && entry.key == null) || key.equals(entry.key)){
				items--;
				if(next == null){
					if(prev == null){
						entries[index] = null;
						return value;
					}
					prev.next = null;
					return value;
				}
				entry.key = next.key;
				entry.value = next.value;
				entry.next = next.next;
				return value;
			}
			entry = entry.next;
		}
		return null;
	}

	public float load(){
		int numItems = 0;
		for(int x=0; x<entries.length; x++){
			Entry<K, V> head = entries[x];
			if(head != null){
				numItems++;
				head = head.next;
			}
		}
		if(entries.length == 0){
			return 0;
		}
		return numItems/entries.length;
	}

	public int hash(K key){
		if(key == null){
			return 0;
		}
		return Math.abs(key.hashCode() % entries.length);
	}

	@Override
	public boolean equals(Object o){
		if(o == this){
			return true;
		}
		if(o == null || !(o instanceof CustomHashMap)){
			return false;
		}
		CustomHashMap<K, V> other = (CustomHashMap<K, V>) o;
		if(entries.length != other.entries.length || this.items != other.items){
			return false;
		}
		for(int x=0; x<entries.length; x++){
			Entry<K, V> customEntry = other.entries[x];
			Object thisEntry = this.entries[x];
			if(customEntry == null && thisEntry == null){
				return true;
			}
			if(customEntry != null && thisEntry != null && !customEntry.equals(thisEntry)){
				return false;
			}
		}
		return true;
	}

	public String toString(){
		String str = "CustomHashMap:\nSIZE: " + entries.length+"\nITEMS: " + items + "\nLOAD: " + load() + "\n";
		for(int x=0; x<entries.length; x++){
			Entry<K, V> entry = entries[x];
			str += x + ": [" + ((entry == null) ? "null" : entry.toString()) + "]\n";
		}
		return str;
	}

}
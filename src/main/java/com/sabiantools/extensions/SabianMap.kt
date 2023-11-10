package com.sabiantools.extensions

fun <K, V> Map<K, V>.toHashMap(): HashMap<K, V> {
    return HashMap(this)
}

inline fun <K, V> HashMap<K, V?>.getOrPutOnlyIfKeyAbsent(key: K, defaultValue: () -> V?): V? {
    if (containsKey(key))
        return get(key)
    return getOrPut(key, defaultValue)
}


inline fun <K, V> HashMap<K, V?>.getOrPutIfValueIs(key: K, compare: V?, defaultValue: () -> V?): V? {
    val value = get(key)
    if (value == compare) {
        val answer = defaultValue()
        put(key, answer)
        return answer
    }
    return getOrPut(key, defaultValue)
}
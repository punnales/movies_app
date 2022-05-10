package com.punnales.moviesapp.data.local.datastore.serializer

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import com.punnales.moviesapp.UserProto
import com.punnales.moviesapp.UserProto.parseFrom
import java.io.InputStream
import java.io.OutputStream

object UserProtoSerializer : Serializer<UserProto> {

    override val defaultValue: UserProto
        get() = UserProto.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): UserProto {
        try {
            return parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: UserProto, output: OutputStream) =
        t.writeTo(output)

}





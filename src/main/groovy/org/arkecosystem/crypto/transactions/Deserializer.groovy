package org.arkecosystem.crypto.transactions


import org.arkecosystem.crypto.enums.Types
import org.arkecosystem.crypto.identities.Address
import org.arkecosystem.crypto.transactions.deserializers.Transfer

import java.nio.ByteBuffer
import java.nio.ByteOrder

import static com.google.common.io.BaseEncoding.base16
import static javax.xml.bind.DatatypeConverter.parseHexBinary

class Deserializer {
    String serialized
    ByteBuffer buffer
    Transaction transaction

    Transaction deserialize(String serialized) {
        this.serialized = serialized

        this.buffer = ByteBuffer.wrap(parseHexBinary(serialized)).slice()
        this.buffer.order(ByteOrder.LITTLE_ENDIAN)
        this.buffer.get()

        this.transaction = new Transaction()

        int assetOffset = deserializeHeader()
        deserializeTypeSpecific(assetOffset)
        deserializeVersionOne()

        this.transaction
    }

    int deserializeHeader() {
        transaction.version = this.buffer.get()
        transaction.network = this.buffer.get()
        transaction.type = this.buffer.get()
        transaction.timestamp = this.buffer.getInt()

        def senderPublicKey = new byte[33]
        this.buffer.get(senderPublicKey)
        transaction.senderPublicKey = base16().lowerCase().encode(senderPublicKey)

        transaction.fee = this.buffer.getLong()

        int vendorFieldLength = this.buffer.get()
        if (vendorFieldLength > 0) {
            def vendorFieldHex = new byte[vendorFieldLength]
            this.buffer.get(vendorFieldHex)
            transaction.vendorFieldHex = base16().lowerCase().encode(vendorFieldHex)
        }

        return (41 + 8 + 1) * 2 + vendorFieldLength * 2
    }

    void deserializeTypeSpecific(int assetOffset) {
        switch (transaction.type) {
            case Types.TRANSFER.getValue():
                new Transfer(this.serialized, this.buffer, this.transaction).deserialize(assetOffset)
            break

            // case Types.SECOND_SIGNATURE_REGISTRATION.getValue():
            //     deserializeSecondSignatureRegistration(this.serialized, this.buffer, this.transaction).deserialize(assetOffset)
            // break

            // case Types.DELEGATE_REGISTRATION.getValue():
            //     deserializeDelegateRegistration(this.serialized, this.buffer, this.transaction).deserialize(assetOffset)
            // break

            // case Types.VOTE.getValue():
            //     deserializeVote(this.serialized, this.buffer, this.transaction).deserialize(assetOffset)
            // break

            // case Types.MULTI_SIGNATURE_REGISTRATION.getValue():
            //     deserializeMultiSignatureRegistration(this.serialized, this.buffer, this.transaction).deserialize(assetOffset)
            // break

            // case Types.IPFS.getValue():
            //     deserializeIpfs(this.serialized, this.buffer, this.transaction).deserialize(assetOffset)
            // break

            // case Types.TIMELOCK_TRANSFER.getValue():
            //     deserializeTimelockTransfer(this.serialized, this.buffer, this.transaction).deserialize(assetOffset)
            // break

            // case Types.MULTI_PAYMENT.getValue():
            //     deserializeMultiPayment(this.serialized, this.buffer, this.transaction).deserialize(assetOffset)
            // break

            // case Types.DELEGATE_RESIGNATION.getValue():
            //     deserializeDelegateResignation(this.serialized, this.buffer, this.transaction).deserialize(assetOffset)
            // break
        }
    }

    void deserializeVersionOne() {
        if (!transaction.amount) {
            transaction.amount = 0
        }

        if (transaction.secondSignature) {
            transaction.signSignature = transaction.secondSignature
        }

        if (Types.VOTE.getValue() == transaction.type) {
            transaction.recipientId = Address.fromPublicKey(transaction.senderPublicKey)
        }

        // if (Types.MULTI_SIGNATURE_REGISTRATION.getValue() == transaction.type) {
        //     transaction.asset['multisignature']['keysgroup'] = array_map(function ($key) {
        //         return '+'.$key
        //     }, transaction.asset['multisignature']['keysgroup'])
        // }
        //

        if (transaction.vendorFieldHex) {
            transaction.vendorField = new String(base16().lowerCase().decode(transaction.vendorFieldHex))
        }

        if (!transaction.id) {
            transaction.id = transaction.getId()
        }

        if (Types.SECOND_SIGNATURE_REGISTRATION.getValue() == transaction.type) {
            transaction.recipientId = Address.fromPublicKey(transaction.senderPublicKey)
        }

        if (Types.MULTI_SIGNATURE_REGISTRATION.getValue() == transaction.type) {
            transaction.recipientId = Address.fromPublicKey(transaction.senderPublicKey)
        }
    }
}

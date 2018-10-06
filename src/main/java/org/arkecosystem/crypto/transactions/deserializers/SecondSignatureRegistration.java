package org.arkecosystem.crypto.transactions.deserializers;

import org.arkecosystem.crypto.transactions.Transaction;

import java.nio.ByteBuffer;

public class SecondSignatureRegistration extends AbstractDeserializer {
    public SecondSignatureRegistration(String serialized, ByteBuffer buffer, Transaction transaction) {
        super(serialized, buffer, transaction);
    }

    public void deserialize(int assetOffset) {
        this.transaction.asset.signature.publicKey = this.serialized.substring(assetOffset, assetOffset + 66);
        this.transaction.parseSignatures(this.serialized, assetOffset + 66);
    }

}

package org.arkecosystem.crypto.transactions.serializers

import org.arkecosystem.crypto.transactions.Transaction
import java.nio.ByteBuffer

abstract class AbstractSerializer {
    ByteBuffer buffer
    Transaction transaction

    AbstractSerializer(ByteBuffer buffer, Transaction transaction) {
        this.buffer = buffer
        this.transaction = transaction
    }
}

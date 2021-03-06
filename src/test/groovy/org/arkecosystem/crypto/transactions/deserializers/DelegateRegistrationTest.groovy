package org.arkecosystem.crypto.transactions.deserializers

import org.arkecosystem.crypto.FixtureLoader
import spock.lang.Specification
import org.arkecosystem.crypto.transactions.*

import java.util.List
import com.google.gson.Gson

class DelegateRegistrationTest extends Specification {
    def "passphrase"() {
        setup:
            Object fixture = FixtureLoader.load(getClass(), "transactions/delegate_registration/passphrase")
        when:
            def actual = new Deserializer().deserialize(fixture.serialized)
        then:
            actual.type == fixture.data.type
            actual.amount == fixture.data.amount
            actual.fee == fixture.data.fee
            actual.recipientId == fixture.data.recipientId
            actual.timestamp == fixture.data.timestamp
            actual.senderPublicKey == fixture.data.senderPublicKey
            actual.signature == fixture.data.signature
            actual.signSignature == fixture.data.signSignature
            actual.asset.delegate.username == fixture.data.asset.delegate.username
            actual.id == fixture.data.id
    }

    def "second-passphrase"() {
        setup:
            Object fixture = FixtureLoader.load(getClass(), "transactions/delegate_registration/second-passphrase")
        when:
            def actual = new Deserializer().deserialize(fixture.serialized)
        then:
            actual.type == fixture.data.type
            actual.amount == fixture.data.amount
            actual.fee == fixture.data.fee
            actual.recipientId == fixture.data.recipientId
            actual.timestamp == fixture.data.timestamp
            actual.senderPublicKey == fixture.data.senderPublicKey
            actual.signature == fixture.data.signature
            actual.signSignature == fixture.data.signSignature
            actual.asset.delegate.username == fixture.data.asset.delegate.username
            actual.id == fixture.data.id
    }
}

package org.arkecosystem.crypto.enums;

public enum Fees {
    TRANSFER(10_000_000L),
    SECOND_SIGNATURE_REGISTRATION(500_000_000L),
    DELEGATE_REGISTRATION(2_500_000_000L),
    VOTE(100_000_000L),
    MULTI_SIGNATURE_REGISTRATION(500_000_000L),
    IPFS(0L),
    TIMELOCK_TRANSFER(0L),
    MULTI_PAYMENT(0L),
    DELEGATE_RESIGNATION(0L);

    Fees(Long value) {
        this.value = value;
    }

    public Long getValue() {
        return value;
    }

    private final Long value;
}
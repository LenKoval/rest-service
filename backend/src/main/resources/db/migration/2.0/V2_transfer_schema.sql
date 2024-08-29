create table transfers
(
    id                        bigserial primary key,
    source_client_id          bigint,
    source_client_number      varchar(16),
    destination_client_id     bigint,
    destination_client_number varchar(16),
    amount                    numeric(6, 2),
    status                    varchar(11),
    updated_at                timestamp
);
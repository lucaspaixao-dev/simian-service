db.createUser(
        {
            user: "simian",
            pwd: "1234",
            roles: [
                {
                    role: "readWrite",
                    db: "simian_service"
                }
            ]
        }
);

db.dna.createIndex( { "id": 1 } )
db.dna.createIndex( { "dna": 1 } )
db.dna.createIndex( { "type": 1 } )

# Clone the repository
git clone [repository_url]
cd [repository_name]

# Build the project
./mvnw clean install

# Run the application
./mvnw spring-boot:run


ndpoints
GET /api/persons/

Description: Retrieves a list of all PersonDto entries from the database.
Returns: List<PersonDto>
GET /api/persons/by-last-name/{lastName}

Description: Fetches a list of PersonDto objects filtered by the last name.
Parameters:
lastName: Path variable specifying the last name to filter by.
Returns: List<PersonDto>
GET /api/persons/by-first-name/{firstName}

Description: Retrieves a list of PersonDto objects filtered by the first name.
Parameters:
firstName: Path variable specifying the first name to filter by.
Returns: List<PersonDto>
GET /api/persons/by-mobile/{mobile}

Description: Fetches a list of PersonDto objects based on the mobile number.
Parameters:
mobile: Path variable specifying the mobile number to filter by.
Returns: List<PersonDto>
GET /api/persons/by-contract-type/{contractType}

Description: Retrieves a list of PersonDto objects filtered by the contract type.
Parameters:
contractType: Path variable (ContractType enum) specifying the type of contract.
Returns: List<PersonDto>
POST /api/persons/{contractType}

Description: Creates a new Person in the database based on the provided PersonDto and contract type.
Parameters:
type: Path variable (ContractType enum) defining the contract type.
person: Request body containing the PersonDto data.
Returns: ResponseEntity<String> with either a success message or an error message.
DELETE /api/persons/{id}

Description: Deletes a Person from the database based on the ID.
Parameters:
id: Path variable specifying the ID of the Person to delete.
Returns: boolean indicating the success of the operation.
PUT /api/persons/{id}

Description: Updates a Person record in the database.
Parameters:
id: Path variable specifying the ID of the Person to update.
person: Request body containing the updated Person data.
Actions: Modifies the specified Person entry based on the provided data.
    
Here is a brief example of how to use the GET endpoint to retrieve all person:    
    {
        "firstName": "Algebra",
        "lastName": "Algebraiczna",
        "mobile": "623462382",
        "email": "algebra@example.com",
        "pesel": "0465784214",
        "type": "EXTERNAL"
    }

Feature: Validationg Place API's
@AddPlace
Scenario Outline: Verify if place is addedd succesfully using AddPlaceAPI
     Given Add Place Payload with "<name>" "<language>" "<address>"
     When User calls "AddPlaceAPI" with "POST" http request
     Then the API call is success with status code as 200
     And "status" in response body is "OK" 
     And "scope" in response body is "APP"
     And verify place id created maps to "<name>" using "GetPlaceAPI"         
Examples:

     |name | language |address |
     |RiyaVerma | English  |12673 Pimple Pune |
    # |AdityaV | English  |456 Pimple Pune |
    
@DeletePlace  
Scenario: Verify if Delet Place functionality working
     Given DeletPlace Payload
     When User calls "DeletePlaceAPI" with "POST" http request
     Then the API call is success with status code as 200
     And "status" in response body is "OK" 
let idOfcomment;
let personlist;

document.getElementById("filterText").addEventListener("input", () => {
    let inputValue = document.getElementById("filterText").value;
    console.log(document.getElementById("filterText").value);
    if(inputValue == "") {
        let newList = personlist.map(person => "<div class='d-flex flex-row justify-content-center mb-2'><a class='p-2 w-50'href= /users/" + person.userUrl + ">" + person.firstName + " " + person.lastName + "</a><button onclick='addAsFriend(\"" + person.userUrl + "\")'>Add friend</button></div>")
               .join('');
        document.getElementById("modalText").innerHTML = newList;
    } else{

        let newList = personlist.filter(person => 
            person.firstName.includes(inputValue) || 
            person.lastName.includes(inputValue));
        
        console.log(newList);
        newList = newList.map(person => "<div class='d-flex flex-row justify-content-center mb-2'><a class='p-2 w-50'href= /users/" + person.userUrl + ">" + person.firstName + " " + person.lastName + "</a><button onclick='addAsFriend(\"" + person.userUrl + "\")'>Add friend</button></div>")
               .join('');
        document.getElementById("modalText").innerHTML = newList;  
    }

    
});

launchModal = async() => {
    $("#exampleModal").modal('show');
    let data = await fetch("/allpersons");
    data = await data.json();
    console.log(data);
    tempdata = data.map(person => "<div class='d-flex flex-row justify-content-center mb-2'><a class='p-2 w-50'href= /users/" + person.userUrl + ">" + person.firstName + " " + person.lastName + "</a><button onclick='addAsFriend(\"" + person.userUrl + "\")'>Add friend</button></div>")
               .join('');
    document.getElementById("modalText").innerHTML = tempdata;
    personlist = data;
}

addAsFriend = async(userUrl) => {
    console.log(userUrl);
    let body = "userUrl=" + userUrl;
    let response = await fetch("/api/addFriendRequest",{
        headers:{
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        method: "POST",
        body: body
    })
    if(response.ok){
        console.log("viesti meni perille");
    } else{
        alert("Friend request already sent");
    }

}

acceptAsFriend = async(id) => {
    console.log(id);
    let body = "id=" + id;
    let response = await fetch("/api/acceptAsFriend",{
        headers:{
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        method: "POST",
        body: body
    })
    if (response.ok) {
        window.location.href = "/profile";
    }

}

declineAsFriend = async(id) => {
    console.log(id);
    let body = "id=" + id;
    let response = await fetch("/api/declineAsFriend",{
        headers:{
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        method: "POST",
        body: body
    })
    if (response.ok) {
        window.location.href = "/profile";
    }

}

removeFriend = async(id) => {
    let body = "id=" + id;
    let response = await fetch("/api/removeFriend",{
        headers:{
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        method: "POST",
        body: body
    })
    if (response.ok) {
        window.location.href = "/profile";
    }
}

openCommentModal = (postId) => {
    $("#commentModal").modal('show');
    idOfcomment = postId;
}

addComment = async() => {
    let body = "id=" + idOfcomment + "&content=" + document.getElementById("commentInput").value;
    
    let response = await fetch("/api/addMessageToPost",{
        headers:{
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        method: "POST",
        body: body
    })
    if (response.ok) {
        window.location.reload();
    }  
}

likePost = async(postId) => {
    let body = "postId=" + postId;
    
    let response = await fetch("/api/likePost",{
        headers:{
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        method: "POST",
        body: body
    })
    if (response.ok) {
        window.location.reload();
    } else{
        alert("You have already liked this post");
    }
}
likeSkill = async(skillId) => {
    let body = "skillId=" + skillId;
    
    let response = await fetch("/api/likeSkill",{
        headers:{
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        method: "POST",
        body: body
    })
    if (response.ok) {
        window.location.reload();
    } else{
        alert("persiilleen män")
    }
}


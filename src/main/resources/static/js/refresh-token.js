function refreshToken() {
    return new Promise((resolve, reject) => {
        fetch('/api/auth/refresh-token', {
            method: 'GET',
            credentials: 'include',
            headers: {
                'Authorization': `Bearer ${localStorage.getItem('refreshToken')}`,
            },
        })
            .then(response => {
                if (response.ok) {
                    return response.text()
                } else {
                    console.log('Refresh token expired!');
                    window.location.replace('/users/login')
                }
            })
            .then(responseData => {
                let newAccessToken = (JSON.parse(responseData)).accessToken;
                localStorage.setItem('accessToken', newAccessToken);
                console.log('token refreshed!');
            })
            .catch(error => {
                console.log(error)
            });
        resolve('success');
        reject('fail')
    });
}
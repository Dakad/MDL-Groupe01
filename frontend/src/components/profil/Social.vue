<template>
    <div class="social">
        <div class ="column">
            <h2>Social Informations</h2>
            <ul class="social-icons">
                <li><a href="http://www.facebook.com"><img src="@/assets/Facebook_Logo.png" /></a></li>
                <li><a href="http://www.twitter.com"><img src="@/assets/Twitter_Logo.png" /></a></li>
                <li><a href="http://www.youtube.com"><img src="@/assets/Linkedin_logo.png" /></a></li>
            </ul>
            <md-card>
                <md-card-header>
                    <div class="md-title">Biography</div>
                </md-card-header>

                <md-card-content>
               Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur sollicitudin enim felis, quis efficitur magna tincidunt non. Proin sit amet leo erat. Sed quis turpis libero. Quisque tincidunt cursus augue. Donec malesuada leo non odio efficitur lobortis. Fusce aliquet arcu dolor, nec pellentesque ex viverra in. Vestibulum ultrices purus magna, ac maximus odio sagittis nec. Nullam efficitur metus quis diam molestie finibus. Proin varius, risus in finibus semper, elit risus placerat diam, vitae faucibus felis sem vitae nisl. Pellentesque sit amet fringilla arcu. Suspendisse aliquam lacinia euismod. Etiam mollis augue vel blandit elementum. Suspendisse commodo suscipit elit vel ultrices. Aliquam aliquam aliquam scelerisque.

Suspendisse vel dictum felis. Praesent sed rhoncus magna. Vestibulum at volutpat elit. Quisque velit neque, efficitur congue maximus sed, faucibus a tortor. Sed id suscipit dui. Nulla aliquam cursus tellus ac tincidunt. Suspendisse non neque sit amet neque cursus varius. Integer quis convallis dui. Donec iaculis nisl quis erat dictum, nec vulputate ipsum fringilla. Donec dignissim suscipit enim sit amet dignissim.

Integer ac maximus diam. Aliquam erat volutpat. Nam vitae venenatis urna, in lacinia purus. Vestibulum facilisis tristique ipsum eu viverra. Integer et maximus magna, eget gravida metus. Pellentesque arcu dui, consectetur quis consequat ut, pharetra et libero. Suspendisse cursus lacinia tellus, at luctus ante ullamcorper porttitor. Curabitur et velit ac magna pulvinar pellentesque.

Sed venenatis quam eget est sagittis aliquam. Sed tempor est ante, id volutpat odio fermentum eu. Nullam tempor urna nibh, quis consequat odio feugiat ut. Morbi dapibus viverra ullamcorper. In urna urna, scelerisque at interdum et, rhoncus a mi. Nullam egestas nulla et dolor aliquet accumsan. Aliquam efficitur non sapien eu rhoncus. Cras non mi vel leo rutrum condimentum. Maecenas scelerisque diam eget justo egestas hendrerit. Donec pharetra vitae lectus ut porttitor. Pellentesque nec tortor pretium, semper dolor at, auctor lacus. Nullam in diam euismod, consectetur ligula et, sagittis tellus. In vitae tincidunt magna, ac pretium ante. Suspendisse tempor mollis purus, quis vehicula nisi convallis eget. Maecenas arcu lorem, faucibus id risus non, vestibulum venenatis nisi.

Vivamus aliquam urna eget metus tincidunt dignissim. Duis lobortis nunc id diam maximus efficitur. Praesent a felis sodales velit sagittis dapibus quis sit amet massa. Mauris nulla odio, molestie a eros quis, ullamcorper semper tortor. Etiam fringilla ornare enim id blandit. Maecenas erat orci, mattis a aliquet at, porta sit amet elit. Aenean dictum facilisis sem et pulvinar. In ac tellus fermentum, tempus purus at, viverra massa.
                </md-card-content>
            </md-card>

        </div>
        <div class ="column">
            <h2>Followed</h2>
        </div>
        <div class ="column">
            <h2>Followers</h2>
        </div>
    </div>
</template>

<script>
export default {
    
}
</script>
<style scoped>
.column {
  float: left;
  width: 33.33%;
  padding: 5px;
}

.social-icons li {
  display:inline-block;
  list-style-type:none;
  -webkit-user-select:none;
  -moz-user-select:none;
  }
.social-icons li a {
  border-bottom: none;
  }
.social-icons li img {
  width:70px;
  height:70px;
  margin-right: 20px;
}
</style>



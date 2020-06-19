import { Component, OnInit } from '@angular/core';
import {NewsFeedService} from '../../../services/news-feed.service';
import { AuthService } from 'src/app/services/auth.service';
import { LOCALE_ID, Inject } from '@angular/core';

@Component({
  selector: 'app-news-feed',
  templateUrl: './news-feed.component.html',
  styleUrls: ['./news-feed.component.scss']
})
export class NewsFeedComponent implements OnInit {
  feed: any;
  message: string;

  constructor(private newsFeedService: NewsFeedService, private authService: AuthService, @Inject(LOCALE_ID) public locale: string) { }


  ngOnInit(): void {
    this.newsFeedService.getFeed().subscribe(feed => {
      this.feed = feed;
    });
  }

  postFeed() {
    const post = {idUser: this.authService.getUser(), message: this.message};
    this.newsFeedService.postFeed(post).subscribe(data => {
      if (this.locale === 'en') {
        alert('Post created');
      } else {
        alert('Post creado');
      }
      window.location.reload();
    },
    error => {
      console.log(error);
    });
  }

}

import { ChangeDetectorRef, Component, ElementRef, Input, OnChanges, Renderer2, SimpleChanges, ViewChild } from '@angular/core';
import { ReviewService } from '../review.service';
import { ActivatedRoute } from '@angular/router';
import { CommentDTO } from '../model/comment.model.dto';
import { AuthenticationService } from '../../authentication/authentication.service';

@Component({
  selector: 'app-comments-ratings',
  templateUrl: './comments-ratings.component.html',
  styleUrl: './comments-ratings.component.css'
})
export class CommentsRatingsComponent implements OnChanges {
  @ViewChild('wrapper', { static: true }) wrapper: ElementRef;
  @Input() load : boolean;
  ownerId: number;
  comments: CommentDTO[];
  loading: boolean = false;

  constructor(private reviewServise: ReviewService, private route: ActivatedRoute, private cdr: ChangeDetectorRef,
    private authenticationService: AuthenticationService, private renderer: Renderer2) { }
 
  ngOnChanges(changes: SimpleChanges): void {
    if(this.load){
      this.reviewServise.getOwnerComments(this.ownerId).subscribe({
        next: (comments: CommentDTO[]) => {
          this.comments = comments;
        }
      });
    }
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.ownerId = +params['userId'];
    });
    if(Number.isNaN(this.ownerId)){
      this.ownerId = this.authenticationService.getUserId();
      this.renderer.addClass(this.wrapper.nativeElement, 'wrapper');
    }
    if (!Number.isNaN(this.ownerId)) {
      this.reviewServise.getOwnerComments(this.ownerId).subscribe({
        next: (comments: CommentDTO[]) => {
          this.comments = comments;
        }
      });
    }
  }

  onEmitChange(data: boolean): void {
    this.reviewServise.getOwnerComments(this.ownerId).subscribe({
      next: (comments: CommentDTO[]) => {
        this.comments = comments;
        this.loading = true;
        this.load = false;
        this.cdr.detectChanges();
      }
    });
  }

  onLoadingChange(data: boolean): void {
    this.loading = false; 
  }
}

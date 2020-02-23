
#import "RNNotificationBanner.h"

@implementation RNNotificationBanner

RCTResponseSenderBlock _onClickCallback = nil;
RCTResponseSenderBlock _onHideCallback = nil;

Banner *_banner = nil;

- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}
RCT_EXPORT_MODULE()



RCT_EXPORT_METHOD(Show:(NSDictionary *)props onClick:(RCTResponseSenderBlock)onClick onHide:(RCTResponseSenderBlock)onHide) {
    _onClickCallback = onClick;
    _onHideCallback = onHide;
    
    NSNumber *type = [props objectForKey: @"type"];
    
    NSString *title = [props objectForKey: @"title"];
    NSNumber *titleSize = [props objectForKey: @"titleSize"];
    NSString *titleColorValue = [props objectForKey: @"titleColor"];

    NSString *subTitle = [props objectForKey: @"subTitle"];
    NSNumber *subTitleSize = [props objectForKey: @"subTitleSize"];
    NSString *subTitleColor = [props objectForKey: @"subTitleColor"];

    NSNumber *duration = [props objectForKey: @"duration"];
    NSNumber *enableProgress = [props objectForKey: @"enableProgress"];
    NSString *tintColorValue = [props objectForKey: @"tintColor"];
    
    NSNumber *withIcon = [props objectForKey: @"withIcon"];
    NSDictionary *icon = [props objectForKey: @"icon"];
    UIImage *drawable = nil;
    
    UIColor *tintColor = nil;
    UIColor *titleColor = nil;
    
//    CSToastStyle *style = [[CSToastStyle alloc] initWithDefaultStyle];
    
    if (icon != nil && [icon count] > 0 && [withIcon intValue] == 1) {
        drawable = [RNImageHelper GenerateImage: icon];
    }
    if (tintColorValue != nil && [tintColorValue length] > 0) {
        tintColor = [RNImageHelper ColorFromHexCode: tintColorValue];
    }
    if (drawable != nil) {
//        style.imageSize = drawable.size;
    }
    if (titleColorValue != nil && [titleColorValue length] > 0) {
        titleColor = [RNImageHelper ColorFromHexCode: titleColorValue];
    }
    //    if (titleSize != 0) {
    //        style.titleFont = [UIFont systemFontOfSize: [titleSize intValue]];
    //    }
    
    _banner = [[Banner alloc] initWithTitle:title subtitle:subTitle image:drawable enableProgress:[enableProgress boolValue] backgroundColor:tintColor didTapBlock:nil];
    
    if (titleColor != nil) {
        _banner.titleLabel.textColor = titleColor;
    }
    _banner.didTapBlock = ^{
        if (_onClickCallback != nil) _onClickCallback(@[]);
        
        _onClickCallback = nil;
        _onHideCallback = nil;
    };
    _banner.didDismissBlock = ^{
        if (_onHideCallback != nil) _onHideCallback(@[]);
        
        _onClickCallback = nil;
        _onHideCallback = nil;
    };

    if ([duration intValue] == 0) {
        [_banner show];
    } else {
        [_banner showWithDuration: [duration intValue]];
    }
}

RCT_EXPORT_METHOD(Dismiss) {
    if (_banner == nil) return;
    
    [_banner dismiss];
}

@end
  

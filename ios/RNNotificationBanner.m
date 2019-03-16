
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
        drawable = [self generateVectorIcon: icon];
    }
    if (tintColorValue != nil && [tintColorValue length] > 0) {
        tintColor = [RNNotificationBanner ColorFromHexCode: tintColorValue];
    }
    if (drawable != nil) {
//        style.imageSize = drawable.size;
    }
    if (titleColorValue != nil && [titleColorValue length] > 0) {
        titleColor = [RNNotificationBanner ColorFromHexCode: titleColorValue];
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

- (UIImage *) generateVectorIcon: (NSDictionary *) icon {
    NSString *family = [icon objectForKey: @"family"];
    NSString *name = [icon objectForKey: @"name"];
    NSString *glyph = [icon objectForKey: @"glyph"];
    NSNumber *size = [icon objectForKey: @"size"];
    NSString *color = [icon objectForKey: @"color"];
    
    if (name != nil && [name length] > 0 && [name containsString: @"."]) {
        return [UIImage imageNamed: name];
    }
    
    UIColor *uiColor = [RNNotificationBanner ColorFromHexCode: color];
    CGFloat screenScale = RCTScreenScale();
    
    UIFont *font = [UIFont fontWithName:family size:[size floatValue]];
    NSAttributedString *attributedString = [[NSAttributedString alloc] initWithString:glyph attributes:@{NSFontAttributeName: font, NSForegroundColorAttributeName: uiColor}];
    
    CGSize iconSize = [attributedString size];
    UIGraphicsBeginImageContextWithOptions(iconSize, NO, 0.0);
    [attributedString drawAtPoint:CGPointMake(0, 0)];
    
    UIImage *iconImage = UIGraphicsGetImageFromCurrentImageContext();
    UIGraphicsEndImageContext();
    
    return iconImage;
}

+ (UIColor *) ColorFromHexCode:(NSString *)hexString {
    NSString *cleanString = [hexString stringByReplacingOccurrencesOfString:@"#" withString:@""];
    if([cleanString length] == 3) {
        cleanString = [NSString stringWithFormat:@"%@%@%@%@%@%@",
                       [cleanString substringWithRange:NSMakeRange(0, 1)],[cleanString substringWithRange:NSMakeRange(0, 1)],
                       [cleanString substringWithRange:NSMakeRange(1, 1)],[cleanString substringWithRange:NSMakeRange(1, 1)],
                       [cleanString substringWithRange:NSMakeRange(2, 1)],[cleanString substringWithRange:NSMakeRange(2, 1)]];
    }
    if([cleanString length] == 6) {
        cleanString = [cleanString stringByAppendingString:@"ff"];
    }
    
    unsigned int baseValue;
    [[NSScanner scannerWithString:cleanString] scanHexInt:&baseValue];
    
    float red = ((baseValue >> 24) & 0xFF)/255.0f;
    float green = ((baseValue >> 16) & 0xFF)/255.0f;
    float blue = ((baseValue >> 8) & 0xFF)/255.0f;
    float alpha = ((baseValue >> 0) & 0xFF)/255.0f;
    
    return [UIColor colorWithRed:red green:green blue:blue alpha:alpha];
}

@end
  

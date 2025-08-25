class Solution:
    def twoSum(self, nums: List[int], target: int) -> List[int]:
        nums1 = nums[:]
        for i in range(len(nums1)):
            nums2 = nums1[:]
            del nums2[i]

            for j in range(len(nums2)):

                if target == (nums1[i] + nums2[j]):
                    output = [i, nums1.index(nums2[j])]
                    if output[0] == output[1]:
                        nums3 = nums1[:]
                        del nums3[output[0]]
                        output[1] = (nums3.index(nums2[j]) + 1)
                    return output